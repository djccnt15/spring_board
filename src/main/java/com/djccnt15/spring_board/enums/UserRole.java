package com.djccnt15.spring_board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER")
    ;
    
    private final String value;
}
