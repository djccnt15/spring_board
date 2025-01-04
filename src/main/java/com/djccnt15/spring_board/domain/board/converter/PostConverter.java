package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;

@Converter
public class PostConverter {
    
    public PostEntity toEntity(
        UserEntity user,
        CategoryEntity category
    ) {
        return PostEntity.builder()
            .category(category)
            .author(user)
            .build();
    }
}
