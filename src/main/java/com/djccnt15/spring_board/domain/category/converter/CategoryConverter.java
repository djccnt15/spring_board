package com.djccnt15.spring_board.domain.category.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.domain.category.model.CategoryResponse;

import java.util.Optional;

@Converter
public class CategoryConverter {
    
    public CategoryResponse toResponse(CategoryEntity entity) {
        return CategoryResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
            .tier(entity.getTier())
            .mainId(Optional
                .ofNullable(entity.getParent())
                .map(CategoryEntity::getId)
                .orElse(null))
            .build();
    }
}
