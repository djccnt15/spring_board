package com.djccnt15.spring_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "internal server error")
public class ApiInternalServerException extends RuntimeException {
    public ApiInternalServerException(String message) {
        super(message);
    }
}
