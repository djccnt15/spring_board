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
import java.util.Arrays;
import java.util.List;

// TODO disable this filter if the app is for public service
@Slf4j
@Component
@Order(value = Integer.MIN_VALUE)
public class ProcessTimeFilter implements Filter {
    
    private final List<String> excludeUriList = Arrays.asList(
        "/favicon.ico",
        "/style.css",
        "/bootstrap.min.css",
        "/bootstrap.min.js"
    );
    
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
            if (!excludeUriList.contains(requestWrapper.getRequestURI())) {
                var processTime = (System.currentTimeMillis() - startTime) + "ms";
                
                log.info(
                    "X-Process-Time: {}, uri: {}, method: {}",
                    processTime,
                    requestWrapper.getRequestURI(),
                    requestWrapper.getMethod()
                );
                
                responseWrapper.addHeader("X-Process-Time", processTime);  // add process time to response header
            }
            
            responseWrapper.copyBodyToResponse();  // restore consumed response
        }
    }
}
