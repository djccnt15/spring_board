package com.djccnt15.spring_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "invalid quthor")
public class InvalidAuthorException extends RuntimeException {
    public InvalidAuthorException(String message) {
        super(message);
    }
}
