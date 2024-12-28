package com.djccnt15.spring_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "unique key constraint")
public class DuplicatedKeyException extends RuntimeException {
    public DuplicatedKeyException(String message) {
        super(message);
    }
}
