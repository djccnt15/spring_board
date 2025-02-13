package com.djccnt15.spring_board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStateEnum {
    
    DISABLED(1, "DISABLED"),  // can't login
    LOCKED(2, "LOCKED"),  // need to verify email to unlock account
    BANNED(3, "BANNED"),  // can't write or update post, comment
    ;
    
    private final long id;
    private final String state;
}
