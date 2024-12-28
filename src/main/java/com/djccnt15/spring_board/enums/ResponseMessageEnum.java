package com.djccnt15.spring_board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessageEnum {
    
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete")
    ;
    
    private final String message;
}
