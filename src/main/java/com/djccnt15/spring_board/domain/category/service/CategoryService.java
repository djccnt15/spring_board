package com.djccnt15.spring_board.domain.category.service;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository repository;
    
    public List<CategoryEntity> getCategoryByTier(int tier) {
        return repository.findByTierOrderByName(tier);
    }
    
    public List<CategoryEntity> getCategoryByMain(CategoryEntity parent) {
        return repository.findByParentOrderByName(parent);
    }
}
