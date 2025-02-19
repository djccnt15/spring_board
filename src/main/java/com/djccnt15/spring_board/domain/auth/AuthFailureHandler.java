package com.djccnt15.spring_board.domain.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthFailureHandler implements AuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException ex
    ) throws IOException, ServletException {
        String errorMessage;
        if (ex instanceof DisabledException) {
            errorMessage = "E-mail을 인증해주세요";
        } else if (ex instanceof LockedException) {
            errorMessage = "정지된 사용자입니다.";
        } else if (ex instanceof BadCredentialsException) {
            errorMessage = "사용자ID 또는 비밀번호를 확인해 주세요.";
        } else {
            errorMessage = "Login Failed. Contact to Admin.";
        }
        
        // Redirect to login page with an error parameter
        response.sendRedirect("/user/login?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
    }
}
