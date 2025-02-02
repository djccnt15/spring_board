package com.djccnt15.spring_board.domain.category.service;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.repository.CategoryRepository;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.domain.category.model.CategoryResponse;
import com.djccnt15.spring_board.domain.category.model.enums.CategoryStatusEnum;
import com.djccnt15.spring_board.domain.enums.DefaultCategoryEnum;
import com.djccnt15.spring_board.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository repository;
    private final CategoryConverter converter;
    
    @Value("${app.category.max-pin}")
    private int categoryMaxPin;
    
    public List<CategoryEntity> getCategoryByTier(Integer tier) {
        return repository.findByTierAndIsActiveOrderByName(tier, true);
    }
    
    public List<CategoryEntity> getCategoryByMain(CategoryEntity parent) {
        return repository.findByParentAndIsActiveOrderByName(parent, true);
    }
    
    public void validateName(CategoryCreateRequest request) {
        repository.findFirstByName(request.getName())
            .ifPresent(
                it -> {throw new ApiDuplicatedKeyException("name of category must be unique");}
            );
    }
    
    public void createCategory(CategoryEntity entity) {
        repository.save(entity);
    }
    
    public CategoryEntity getCategory(Long id) {
        return repository.findFirstByIdAndIsActive(id, true)
            .orElseThrow(() -> new ApiDataNotFoundException("can't find requested category"));
    }
    
    public void updateCategory(CategoryEntity entity) {
        repository.save(entity);
    }
    
    public void deleteCategory(CategoryEntity entity) {
        var name = "%s_%s".formatted(entity.getName(), LocalDateTime.now().withNano(0));
        entity.setName(name);
        entity.setActive(false);
        repository.save(entity);
    }
    
    public CategoryEntity getCategory(String categoryName) {
        return repository.findFirstByNameAndIsActive(categoryName, true)
            .orElseThrow(() -> new DataNotFoundException("can't find requested category"));
    }
    
    public Optional<CategoryEntity> getOptionalCategory(String categoryName) {
        return repository.findFirstByNameAndIsActive(categoryName, true);
    }
    
    public List<CategoryResponse> getCategoryByParent(CategoryEntity entity) {
        return entity.getChildren().stream()
            .map(converter::toResponse)
            .toList();
    }
    
    public void validateDefault(CategoryEntity entity) {
        var validateResult = DefaultCategoryEnum.contains(entity.getName());
        if (validateResult) {
            throw new ApiForbiddenException("you can't delete/update default category");
        }
    }
    
    public CategoryResponse setStatus(CategoryResponse category) {
        if (DefaultCategoryEnum.contains(category.getName())) {
            category.setStatus(CategoryStatusEnum.DEFAULT);
        } else if (category.getPinOrder() != null) {
            category.setStatus(CategoryStatusEnum.PINNED);
        } else {
            category.setStatus(CategoryStatusEnum.UNPINNED);
        }
        return category;
    }
    
    public void validatePinCount() {
        var count = repository.countByPinOrderIsNotNull();
        if (count >= categoryMaxPin) {
            throw new ApiBadRequestException("you can't pin more than %s categories".formatted(categoryMaxPin));
        }
    }
    
    public void pinCategory(CategoryEntity entity) {
        repository.findFirstByPinOrderIsNotNullOrderByPinOrderDesc()
            .ifPresentOrElse(
            it -> entity.setPinOrder(it.getPinOrder() + 1),
                () -> entity.setPinOrder(0)
            );
        repository.save(entity);
    }
    
    public void unPinCategory(CategoryEntity entity) {
        entity.setPinOrder(null);
        repository.save(entity);
    }
    
    public void resetPinOrder() {
        var categoryList = repository.findByPinOrderIsNotNullOrderByPinOrder();
        int i = 1;
        for (CategoryEntity categoryEntity : categoryList) categoryEntity.setPinOrder(i++);
        repository.saveAll(categoryList);
    }
}
