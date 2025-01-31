package com.djccnt15.spring_board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class for handling user authority and privilege <br>
 * role field is used for function like <i>hasRole(), hasAuthority()</i>
 */
@Getter
@AllArgsConstructor
public enum UserAuthorityEnum {

    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER"),
    ;
    
    private final String role;
}
