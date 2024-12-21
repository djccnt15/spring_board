package com.djccnt15.spring_board.domain.auth;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.db.repository.UserRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.enums.UserRole;
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
    
    private final UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = repository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        switch ((userEntity.getRole() == null) ? UserRoleEnum.USER : userEntity.getRole()) {
            case ADMIN -> authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
            case STAFF -> authorities.add(new SimpleGrantedAuthority(UserRole.MANAGER.getValue()));
            default -> authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        
        return UserSession.builder()
            .userId(userEntity.getId())
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .authorities(authorities)
            .build();
    }
}
