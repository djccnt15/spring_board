package com.djccnt15.spring_board.exception.advice;

import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.InvalidAuthorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ViewExceptionHandler {
    
    /**
     * basic global Exception handler
     * @param ex runtime exception
     * @param model model injection from spring
     * @return general error page
     */
    @ExceptionHandler(value = {Exception.class})
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
    
    /**
     * DataNotFoundException handler
     * @param ex DataNotFoundException
     * @param model model injection from spring
     * @return error page view
     */
    @ExceptionHandler(value = {DataNotFoundException.class})
    public String handleDataNotFoundException(
        Exception ex,
        Model model
    ) {
        model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorType", ex.getClass().getSimpleName());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-page";
    }
    
    /**
     * un authorize exception handler
     * @param ex UNAUTHORIZED exception
     * @param model inject from spring
     * @return error page view
     */
    @ExceptionHandler(value = {InvalidAuthorException.class})
    public String handleUnAuthorizeException(
        Exception ex,
        Model model
    ) {
        model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.value());
        model.addAttribute("errorType", ex.getClass().getSimpleName());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-page";
    }
}
