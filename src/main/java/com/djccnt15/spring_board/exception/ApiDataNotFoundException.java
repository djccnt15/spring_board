package com.djccnt15.spring_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class ApiDataNotFoundException extends RuntimeException {
    public ApiDataNotFoundException(String message) {
        super(message);
    }
}
