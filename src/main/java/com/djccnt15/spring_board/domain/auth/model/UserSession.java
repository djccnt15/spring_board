package com.djccnt15.spring_board.domain.auth.model;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class UserSession implements UserDetails {
    
    private Long userId;
    
    private String username;
    
    private String password;
    
    private UserRoleEnum role;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    @Builder.Default
    private boolean isEnabled = false;
    
    @Builder.Default
    private boolean isLocked = false;
    
    @Builder.Default
    private boolean isBanned = false;
    
    @Override
    public boolean isEnabled() {  // DisabledException
        return isEnabled;
    }
    
    @Override
    public boolean isAccountNonLocked() {  // LockedException
        return !isLocked;
    }
    
    @Override
    public boolean isAccountNonExpired() {  // AccountExpiredException
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {  // CredentialsExpiredException
        return true;
    }
}
