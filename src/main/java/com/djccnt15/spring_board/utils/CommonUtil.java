package com.djccnt15.spring_board.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public final class CommonUtil {
    
    public static int getTotalPageCount(int total, int size) {
        return (int) Math.ceil((double) total / size);
    }
}
