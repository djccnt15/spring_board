package com.djccnt15.spring_board.domain.admin.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.domain.admin.model.AdminCategoryResponse;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
            return AdminCategoryResponse.builder().build();
        }
        
        var main = mainList.stream()
            .filter(it -> it.getId().equals(mainId)).findFirst()
            .orElse(mainList.get(0));
        var subList = service.getCategoryByMain(main);
        
        return AdminCategoryResponse.builder()
            .mainList(mainList.stream().map(converter::toResponse).toList())
            .subList(subList.stream().map(converter::toResponse).toList())
            .build();
    }
    
    public void createMain(CategoryCreateRequest form) {
        service.validateName(form);
        service.createCategory(form);
    }
    
    public CategoryCreateRequest getCategoryUpdateForm(Long id) {
        var entity = service.getCategory(id);
        return CategoryCreateRequest.builder()
            .tier(entity.getTier())
            .name(entity.getName())
            .mainId(
                Optional.ofNullable(entity.getParent())
                    .map(CategoryEntity::getId)
                    .orElse(null)
            )
            .build();
    }
    
    public void updateCategory(Long id, CategoryCreateRequest form) {
        service.validateName(form);
        var entity = service.getCategory(id);
        service.updateCategory(entity, form);
    }
    
    public void deleteCategory(Long id) {
        var entity = service.getCategory(id);
        service.deleteCategory(entity);
    }
}
