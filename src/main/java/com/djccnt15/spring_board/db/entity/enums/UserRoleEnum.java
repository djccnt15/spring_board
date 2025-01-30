package com.djccnt15.spring_board.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    
    ADMIN("ADMIN"),
    STAFF("STAFF"),
    USER("USER"),
    ;
    
    private final String role;
}
