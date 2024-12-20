package com.djccnt15.spring_board.domain.user.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.domain.user.model.UserResponse;

@Converter
public class UserConverter {
    
    public UserResponse toResponse(UserEntity entity) {
        return UserResponse.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .email(entity.getEmail())
            .createDateTime(entity.getCreateDateTime())
            .role(entity.getRole())
            .build();
    }
}
