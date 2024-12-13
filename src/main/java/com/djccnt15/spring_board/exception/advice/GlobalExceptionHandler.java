package com.djccnt15.spring_board.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * basic global Exception handler
     * @param ex runtime exception
     * @param model model injection from spring
     * @return generic error page
     */
    @ExceptionHandler(Exception.class)
    public String handleException(
        Exception ex,
        Model model
    ) {
        log.error("", ex);
        
        model.addAttribute("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("errorType", ex.getClass().getSimpleName());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-page";
    }
}
