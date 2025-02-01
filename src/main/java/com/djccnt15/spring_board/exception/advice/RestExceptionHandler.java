package com.djccnt15.spring_board.exception.advice;

import com.djccnt15.spring_board.exception.*;
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
        ApiDuplicatedKeyException.class,
        ApiDataNotFoundException.class,
        ApiBadRequestException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
        var errResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build();
        return ResponseEntity.badRequest().body(errResponse);
    }
    
    /**
     * handle UnAuthorized Exception
     * @param ex UnAuthorized Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {ApiInvalidAuthorException.class})
    public ResponseEntity<ErrorResponse> handleInvalidException(Exception ex) {
        var errResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .status(HttpStatus.UNAUTHORIZED)
            .build();
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(errResponse);
    }
    
    /**
     * handle Forbidden Exception
     * @param ex Forbidden Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {ApiForbiddenException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(Exception ex) {
        var errResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .status(HttpStatus.FORBIDDEN)
            .build();
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(errResponse);
    }
}
