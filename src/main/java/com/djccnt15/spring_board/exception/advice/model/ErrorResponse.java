package com.djccnt15.spring_board.exception.advice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    
    private HttpStatus status;
    
    private String message;
}
