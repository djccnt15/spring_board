package com.djccnt15.spring_board.domain.auth;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {
    // TODO: update to force logout of shared stored session
    
    private final SessionRegistry sessionRegistry;
    
    public void forceLogout(String username) {
        sessionRegistry.getAllPrincipals().stream()
            .filter(UserDetails.class::isInstance)
            .map(UserDetails.class::cast)
            .filter(userDetails -> userDetails.getUsername().equals(username))
            .flatMap(userDetails -> sessionRegistry.getAllSessions(userDetails, false).stream())
            .forEach(SessionInformation::expireNow);
    }
    
    public void forceLogout(Long id) {
        sessionRegistry.getAllPrincipals().stream()
            .filter(UserDetails.class::isInstance)
            .map(UserSession.class::cast)
            .filter(userSession -> userSession.getUserId().equals(id))
            .flatMap(userSession -> sessionRegistry.getAllSessions(userSession, false).stream())
            .forEach(SessionInformation::expireNow);
    }
}
