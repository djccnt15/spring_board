package com.djccnt15.spring_board.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
public class RandomString {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*?";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    
    public String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
