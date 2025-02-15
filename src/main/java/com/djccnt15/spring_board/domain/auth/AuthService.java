package com.djccnt15.spring_board.domain.auth;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.service.UserService;
import com.djccnt15.spring_board.enums.UserAuthorityEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Component for Spring Security login
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    
    private final UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userService.getUser(username);
        
        // adding user authorities for hasRole(), hasAuthority()
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch ((userEntity.getRole() == null) ? UserRoleEnum.USER : userEntity.getRole()) {
            case ADMIN -> authorities.add(new SimpleGrantedAuthority(UserAuthorityEnum.ADMIN.getRole()));
            case STAFF -> authorities.add(new SimpleGrantedAuthority(UserAuthorityEnum.MANAGER.getRole()));
            default -> authorities.add(new SimpleGrantedAuthority(UserAuthorityEnum.USER.getRole()));
        }
        
        return UserSession.builder()
            .userId(userEntity.getId())
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .role(userEntity.getRole())
            .authorities(authorities)
            .build();
    }
}
