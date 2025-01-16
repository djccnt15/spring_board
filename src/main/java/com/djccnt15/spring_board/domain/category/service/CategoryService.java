package com.djccnt15.spring_board.domain.category.service;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.repository.CategoryRepository;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.domain.category.model.CategoryResponse;
import com.djccnt15.spring_board.exception.ApiDataNotFoundException;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.DuplicatedKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository repository;
    private final CategoryConverter converter;
    
    public List<CategoryEntity> getCategoryByTier(int tier) {
        return repository.findByTierAndIsActiveOrderByName(tier, true);
    }
    
    public List<CategoryEntity> getCategoryByMain(CategoryEntity parent) {
        return repository.findByParentAndIsActiveOrderByName(parent, true);
    }
    
    public void validateName(CategoryCreateRequest request) {
        repository.findByName(request.getName()).ifPresent(
            it -> {
                throw new DuplicatedKeyException("name of category must be unique");
            }
        );
    }
    
    public void createCategory(CategoryEntity entity) {
        repository.save(entity);
    }
    
    public CategoryEntity getCategory(Long id) {
        return repository.findByIdAndIsActive(id, true).orElseThrow(
            () -> new ApiDataNotFoundException("can't find requested category")
        );
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
        return repository.findByNameAndIsActive(categoryName, true).orElseThrow(
            () -> new DataNotFoundException("can't find requested category")
        );
    }
    
    public List<CategoryResponse> getCategoryByParent(CategoryEntity entity) {
        return entity.getChildren().stream()
            .map(converter::toResponse)
            .toList();
    }
}
