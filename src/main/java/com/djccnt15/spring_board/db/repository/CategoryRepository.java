package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    
    List<CategoryEntity> findByTierOrderByName(int tier);
    
    List<CategoryEntity> findByParentOrderByName(CategoryEntity category);
}
