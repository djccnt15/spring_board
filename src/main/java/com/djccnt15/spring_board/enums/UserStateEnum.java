package com.djccnt15.spring_board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStateEnum {
    
    DISABLED(1, "DISABLED"),  // need to verify email to unlock account
    LOCKED(2, "LOCKED"),  // can't login
    BANNED(3, "BANNED"),  // can't write/update post/comment
    ;
    
    private final long id;
    private final String state;
}
