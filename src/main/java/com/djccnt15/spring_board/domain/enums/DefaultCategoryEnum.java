package com.djccnt15.spring_board.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DefaultCategoryEnum {
    
    QNA("QNA", 1),
    COMMUNITY("COMMUNITY", 1),
    TECH("TECH", 2),
    CAREER("CAREER", 2),
    NEWS("NEWS", 2),
    LIFE("LIFE", 2),
    ;
    
    private final String value;
    private final Integer tier;
    
    public static boolean contains(String category) {
        return Arrays.stream(DefaultCategoryEnum.values())
            .anyMatch(e -> e.value.equalsIgnoreCase(category));
    }
}
