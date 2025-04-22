package com.djccnt15.spring_board.config.security;

import com.djccnt15.spring_board.db.entity.LoggedInEntity;
import com.djccnt15.spring_board.db.repository.LoggedInRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    
    private final UserConverter userConverter;
    private final LoggedInRepository loggedInRepository;
    
    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        var user = (UserSession) authentication.getPrincipal();
        var userEntity = userConverter.toEntity(user);
        var loginHistory = LoggedInEntity.builder()
            .user(userEntity)
            .build();
        loggedInRepository.save(loginHistory);
        response.sendRedirect("/");
    }
}
