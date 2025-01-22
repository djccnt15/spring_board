package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.PostVoterEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;

@Converter
public class PostVoterConverter {
    
    public PostVoterEntity toEntity(
        PostEntity post,
        UserEntity user
    ) {
        return PostVoterEntity.builder()
            .post(post)
            .user(user)
            .build();
    }
}
