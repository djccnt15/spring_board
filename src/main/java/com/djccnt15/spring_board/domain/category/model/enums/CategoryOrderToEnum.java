package com.djccnt15.spring_board.domain.category.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryOrderToEnum {
    
    UP("UP"),
    DOWN("DOWN"),
    ;
    
    private final String to;
}
