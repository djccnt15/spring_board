package com.djccnt15.spring_board.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommonUtil {
    
    public static Integer getTotalPageCount(Integer total, Integer size) {
        return (int) Math.ceil((double) total / size);
    }
}
