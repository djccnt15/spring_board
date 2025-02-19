package com.djccnt15.spring_board.domain.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoItemResponse {
    
    private UserResponse user;
    
    private UserItemListResponse items;
}
