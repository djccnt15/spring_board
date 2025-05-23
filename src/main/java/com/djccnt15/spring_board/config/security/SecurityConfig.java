package com.djccnt15.spring_board.config.security;

import com.djccnt15.spring_board.config.properties.SessionProperties;
import com.djccnt15.spring_board.domain.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final List<String> SWAGGER = List.of(
        "/swagger-ui.html,",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );
    
    private final PasswordEncoder encoder;
    private final AuthService authService;
    private final SessionProperties sessionProperties;
    private final SessionRegistry sessionRegistry;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;
    
    /**
     * Configuration for Spring Security
     * @param http http security injection from spring security
     * @return http result
     * @throws Exception exception for user authority
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf
                // disable CSRF for user email verification page
                .ignoringRequestMatchers("/user/verify/**")
            )
            .authorizeHttpRequests((authorize) -> authorize
                // allow to swagger ui. TODO: disable permit to swagger ui below for production
                // SWAGGER.toArray() returns Object[], not String[] and requestMatchers expects String[]
                // new String[0] ensures that the method dynamically creates a correctly sized array
                .requestMatchers(SWAGGER.toArray(new String[0])).permitAll()
                
                // TODO: disable permit to public for production
                // open prometheus metrics
                .requestMatchers("/actuator/prometheus").permitAll()
                
                // only admin can manage category
                .requestMatchers("/admin/category/**").hasAnyRole("ADMIN")
                
                // admin and manager can manage user
                .requestMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER")
                
                // permit all to rest of every public domain
                .requestMatchers("/**").permitAll()
                
                // any request to rest of domain needs to be authenticated
                .anyRequest().authenticated()
            )
            .formLogin((formLogin) -> formLogin
                .loginPage("/user/login")
                // .defaultSuccessUrl("/")  // success url is handled in AuthSuccessHandler
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
            )
            .sessionManagement(session -> session
                // .invalidSessionUrl("/login?timeout")  // redirect on timeout
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry)
                .expiredUrl("/user/session-expired")  // redirect when session expires
            )
            .rememberMe((remember) -> remember
                .key(sessionProperties.getKey())
                .tokenValiditySeconds(sessionProperties.getTokenValidSec())
                .alwaysRemember(true)
            )
            .logout((logout) -> logout
                // Spring Security uses ExactRequestMatcher for matching the logout URL, which works only for `POST` requests
                // use AntPathRequestMatcher to let client logout via `GET` method
                // not using AntPathRequestMatcher raises `No static resource user/logout.` exception
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
        ;
        return http.build();
    }
    
    /**
     * Bean for Spring Security authentication <br>
     * this bean works with implements of UserDetailsService, PasswordEncoder
     */
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
        authProvider.setPasswordEncoder(encoder);
        return new ProviderManager(authProvider);
    }
}
