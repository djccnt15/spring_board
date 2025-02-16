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
    
    private final List<String> excludeUriPattern = Arrays.asList(
        "/favicon.ico",
        "/bootstrap.min",
        ".js",
        ".css"
    );
    
    private boolean checkExclude(String uri) {
        for (String pattern : excludeUriPattern) {
            if (uri.contains(pattern)) {
                return true;
            }
        }
        return false;
    }
    
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
            var isExcludeUri = checkExclude(requestWrapper.getRequestURI());
            if (!isExcludeUri) {
                var processTime = (System.currentTimeMillis() - startTime) + "ms";
                var hiddenMethod = requestWrapper.getParameter("_method");
                var actualMethod = (hiddenMethod != null) ? hiddenMethod : requestWrapper.getMethod();
                
                log.info(
                    "X-Process-Time: {}, uri: {}, method: {}",
                    processTime,
                    requestWrapper.getRequestURI(),
                    actualMethod
                );
                
                responseWrapper.addHeader("X-Process-Time", processTime);  // add process time to response header
            }
            
            responseWrapper.copyBodyToResponse();  // restore consumed response
        }
    }
}
