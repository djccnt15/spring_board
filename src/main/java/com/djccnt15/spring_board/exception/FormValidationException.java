package com.djccnt15.spring_board.exception;

public class FormValidationException extends RuntimeException {
    public FormValidationException(String message) {
        super(message);
    }
}
