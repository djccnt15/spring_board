package com.djccnt15.spring_board.domain.category.service;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.repository.CategoryRepository;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.exception.DuplicatedKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository repository;
    private final CategoryConverter converter;
    
    public List<CategoryEntity> getCategoryByTier(int tier) {
        return repository.findByTierOrderByName(tier);
    }
    
    public List<CategoryEntity> getCategoryByMain(CategoryEntity parent) {
        return repository.findByParentOrderByName(parent);
    }
    
    public void validateName(CategoryCreateRequest request) {
        repository.findByName(request.getName()).ifPresent(
            it -> {
                throw new DuplicatedKeyException("name of category must be unique");
            }
        );
    }
    
    public void createCategory(CategoryCreateRequest request) {
        repository.save(converter.toEntity(request));
    }
}
