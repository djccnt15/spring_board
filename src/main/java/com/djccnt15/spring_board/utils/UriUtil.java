package com.djccnt15.spring_board.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public final class UriUtil {
    
    public static String getBaseUrl(HttpServletRequest request) {
        return request.getRequestURL()
            .toString()
            .replace(request.getRequestURI(), "");
    }
}
