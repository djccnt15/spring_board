package com.djccnt15.spring_board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessageEnum {
    
    CREATE("CREATED"),
    UPDATE("UPDATED"),
    DELETE("DELETED"),
    ;
    
    private final String message;
}
