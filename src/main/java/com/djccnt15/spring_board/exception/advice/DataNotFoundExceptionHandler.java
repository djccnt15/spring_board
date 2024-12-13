package com.djccnt15.spring_board.exception.advice;

import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.advice.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class DataNotFoundExceptionHandler {
    
    /**
     * DataNotFoundException handler
     * @param ex DataNotFoundException
     * @return ResponseEntity
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        var errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .status(HttpStatus.NOT_FOUND)
            .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
