package com.djccnt15.spring_board.domain.admin.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.domain.admin.model.AdminCategoryResponse;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.domain.category.model.CategoryOrderUpdateRequest;
import com.djccnt15.spring_board.domain.category.model.CategoryResponse;
import com.djccnt15.spring_board.domain.category.model.SubCategoryUpdatePlaceholder;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import com.djccnt15.spring_board.exception.ApiDuplicatedKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Business
@RequiredArgsConstructor
public class AdminCategoryBusiness {
    
    private final CategoryService service;
    private final CategoryConverter converter;
    
    public AdminCategoryResponse getCategories(Long mainId) {
        var mainList = service.getCategoryByTier(1);
        
        if (mainList.isEmpty()) {
            return new AdminCategoryResponse();
        }
        
        var main = mainList.stream()
            .filter(it -> it.getId().equals(mainId)).findFirst()
            .orElse(mainList.get(0));
        var mainCategoryList = mainList.stream()
            .map(converter::toResponse)
            .map(service::setStatus)
            .toList();
        var subList = service.getCategoryByMain(main).stream()
            .map(converter::toResponse)
            .map(service::setStatus)
            .toList();
        return AdminCategoryResponse.builder()
            .mainList(mainCategoryList)
            .subList(subList)
            .build();
    }
    
    public void createMainCategory(CategoryCreateRequest request) {
        service.validateName(request);
        var entity = converter.toEntity(request, 1);
        service.createCategory(entity);
    }
    
    public void createSubCategory(CategoryCreateRequest request) {
        service.validateName(request);
        var mainCategory = service.getCategory(request.getMainId());
        var entity = converter.toEntity(request, 2, mainCategory);
        service.createCategory(entity);
    }
    
    public CategoryCreateRequest getMainCategoryUpdatePlaceholder(Long id) {
        var entity = service.getCategory(id);
        return CategoryCreateRequest.builder()
            .name(entity.getName())
            .mainId(
                Optional.ofNullable(entity.getParent())
                    .map(CategoryEntity::getId)
                    .orElse(null)
            )
            .build();
    }
    
    public void updateMainCategory(Long id, CategoryCreateRequest form) {
        service.validateName(form);
        var entity = service.getCategory(id);
        service.validateDefault(entity);
        entity.setName(form.getName());
        service.updateCategory(entity);
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        var entity = service.getCategory(id);
        service.validateDefault(entity);
        var children = entity.getChildren();
        if (!children.isEmpty()) {
            children.forEach(service::deleteCategory);
        }
        service.deleteCategory(entity);
        service.resetPinOrder();
        service.deletePost(entity);
        service.deleteComment(entity);
    }
    
    public SubCategoryUpdatePlaceholder getSubCategoryUpdatePlaceholder(Long id) {
        var entity = service.getCategory(id);
        var mainList = service.getCategoryByTier(1).stream()
            .map(converter::toResponse)
            .toList();
        var selected = mainList.stream()
            .filter(it -> it.getId().equals(entity.getParent().getId())).findFirst()
            .orElse(mainList.get(0));
        return SubCategoryUpdatePlaceholder.builder()
            .name(entity.getName())
            .selected(selected)
            .mainList(mainList)
            .build();
    }
    
    public void updateSubCategory(Long id, CategoryCreateRequest form) {
        var entity = service.getCategory(id);
        service.validateDefault(entity);
        var parent = service.getCategory(form.getMainId());
        entity.setName(form.getName());
        entity.setParent(parent);
        try {
            service.updateCategory(entity);
        } catch (DataIntegrityViolationException e) {
            throw new ApiDuplicatedKeyException("name of category must be unique");
        }
    }
    
    @Transactional
    public void pinCategory(Long id) {
        var entity = service.getCategory(id);
        if (entity.getPinOrder() == null) {
            service.validatePinCount();
            service.pinCategory(entity);
        } else {
            service.unPinCategory(entity);
            service.resetPinOrder();
        }
    }
    
    public List<CategoryResponse> getPinnedCategory() {
        var categoryList = service.getPinnedCategory();
        return categoryList.stream()
            .map(converter::toResponse)
            .toList();
    }
    
    public void changeOrder(
        Long id,
        CategoryOrderUpdateRequest form
    ) {
        var entity = service.getCategory(id);
        service.changeOrder(entity, form);
    }
}
