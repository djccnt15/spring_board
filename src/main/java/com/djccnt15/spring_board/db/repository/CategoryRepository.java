package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    
    Optional<CategoryEntity> findFirstByName(String name);
    
    List<CategoryEntity> findByTierAndIsActiveOrderByName(Integer tier, Boolean isActive);
    
    List<CategoryEntity> findByParentAndIsActiveOrderByName(CategoryEntity category, Boolean isActive);
    
    Optional<CategoryEntity> findFirstByIdAndIsActive(Long id, Boolean isActive);
    
    Optional<CategoryEntity> findFirstByNameAndIsActive(String categoryName, Boolean isActive);
    
    @Query(value = "SELECT COALESCE(MAX(pinOrder), 0) FROM CategoryEntity")
    Integer getLastPinOrder();
    
    List<CategoryEntity> findByPinOrderIsNotNullOrderByPinOrder();
}
