package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.CommentVoterEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;

@Converter
public class CommentVoterConverter {
    
    public CommentVoterEntity toEntity(
        CommentEntity comment,
        UserEntity user
    ) {
        return CommentVoterEntity.builder()
            .comment(comment)
            .user(user)
            .build();
    }
}
