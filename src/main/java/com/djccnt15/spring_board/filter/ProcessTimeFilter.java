package com.djccnt15.spring_board.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

// TODO disable this filter is the app is for public service
@Slf4j
@Component
@Order(value = Integer.MIN_VALUE)
public class ProcessTimeFilter implements Filter {
    
    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        
        // cashing wrapper for request/response which will be consumed
        var requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        
        var startTime = System.currentTimeMillis();
        
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            var processTime = (System.currentTimeMillis() - startTime) + "ms";
            
            log.info(
                "X-Process-Time: {}, uri: {}, method: {}",
                processTime,
                requestWrapper.getRequestURI(),
                requestWrapper.getMethod()
            );
            
            responseWrapper.addHeader("X-Process-Time", processTime);  // add process time to response header
            responseWrapper.copyBodyToResponse();  // restore consumed response
        }
    }
}
