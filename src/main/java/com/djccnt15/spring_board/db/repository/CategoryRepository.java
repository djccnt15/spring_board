package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    
    Optional<CategoryEntity> findByName(String name);
    
    List<CategoryEntity> findByTierAndIsActiveOrderByName(int tier, boolean isActive);
    
    List<CategoryEntity> findByParentAndIsActiveOrderByName(CategoryEntity category, boolean isActive);
    
    Optional<CategoryEntity> findByIdAndIsActive(Long id, boolean isActive);
    
    Optional<CategoryEntity> findByNameAndIsActive(String categoryName, boolean isActive);
}
