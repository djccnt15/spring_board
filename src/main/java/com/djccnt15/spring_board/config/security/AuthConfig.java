package com.djccnt15.spring_board.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {
    
    /**
     * Bean for password encoding
     */
    @Bean  // make returned object as spring bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Bean for manage session
     */
    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
