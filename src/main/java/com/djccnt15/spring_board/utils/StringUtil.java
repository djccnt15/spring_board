package com.djccnt15.spring_board.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@UtilityClass
public final class StringUtil {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*?";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    
    public static String generateRandomString(int length) {
        var sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
    
    public static String datetimeFormatter(LocalDateTime dateTime, String format) {
        var formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }
}
