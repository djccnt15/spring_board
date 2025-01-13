package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;

@Converter
public class CommentConverter {
    
    public CommentEntity toEntity(
        UserEntity user,
        PostEntity post
    ) {
        return CommentEntity.builder()
            .author(user)
            .post(post)
            .build();
    }
}
