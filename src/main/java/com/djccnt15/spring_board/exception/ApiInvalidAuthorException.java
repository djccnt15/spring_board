package com.djccnt15.spring_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "invalid author")
public class ApiInvalidAuthorException extends RuntimeException {
    public ApiInvalidAuthorException(String message) {
        super(message);
    }
}
