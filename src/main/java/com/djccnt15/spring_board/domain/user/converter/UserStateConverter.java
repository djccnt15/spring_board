package com.djccnt15.spring_board.domain.user.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.StateEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.entity.UserStateEntity;
import com.djccnt15.spring_board.db.entity.id.UserStateId;

@Converter
public class UserStateConverter {
    
    public UserStateEntity toEntity(
        StateEntity state,
        UserEntity user,
        String detail
    ) {
        var id = UserStateId.builder()
            .stateId(state.getId())
            .userId(user.getId())
            .build();
        return UserStateEntity.builder()
            .userStateId(id)
            .state(state)
            .user(user)
            .detail(detail)
            .build();
    }
    
    public UserStateEntity toEntity(
        StateEntity state,
        UserEntity user
    ) {
        var id = UserStateId.builder()
            .stateId(state.getId())
            .userId(user.getId())
            .build();
        return UserStateEntity.builder()
            .userStateId(id)
            .state(state)
            .user(user)
            .build();
    }
}
