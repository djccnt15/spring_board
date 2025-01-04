package com.djccnt15.spring_board.config.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    
    private final AuthService authService;
    
    /**
     * Configuration for Spring Security
     * @param http http security injection from spring security
     * @return http result
     * @throws Exception exception for user authority
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
            (authorize) -> authorize
                // allow to swagger ui. TODO disable permit to swagger ui below for production
                .requestMatchers(SWAGGER.toArray(new String[0])).permitAll()
                
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
                .defaultSuccessUrl("/")
            )
            .logout((logout) -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true))
        ;
        return http.build();
    }
    
    /**
     * Bean for password encoding
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Bean for Spring Security authentication
     * <br>
     * this bean works with implements of UserDetailsService, PasswordEncoder
     */
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }
}
