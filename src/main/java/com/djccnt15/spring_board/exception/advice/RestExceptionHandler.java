package com.djccnt15.spring_board.exception.advice;

import com.djccnt15.spring_board.exception.ApiDataNotFoundException;
import com.djccnt15.spring_board.exception.DuplicatedKeyException;
import com.djccnt15.spring_board.exception.advice.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    
    /**
     * handle Bad Request Exception
     * @param ex Bad Request Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {
        DuplicatedKeyException.class,
        ApiDataNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
        var errResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build();
        return ResponseEntity.badRequest().body(errResponse);
    }
}
