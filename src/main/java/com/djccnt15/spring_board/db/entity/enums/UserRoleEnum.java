package com.djccnt15.spring_board.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class for handling user access level <br>
 * use role field to check DB record
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    
    ADMIN("ADMIN"),
    STAFF("STAFF"),
    USER("USER"),
    ;
    
    private final String role;
}
