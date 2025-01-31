package com.djccnt15.spring_board.domain.category.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryStatusEnum {
    
    DEFAULT("DEFAULT"),
    PINNED("PINNED"),
    UNPINNED("UNPINNED"),
    DELETED("DELETED"),
    ;
    
    private final String status;
}
