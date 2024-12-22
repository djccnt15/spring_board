package com.djccnt15.spring_board.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    
    ADMIN("admin"),
    STAFF("staff"),
    USER("user")
    ;
    
    private final String role;
}
