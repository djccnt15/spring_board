package com.djccnt15.spring_board.domain.user.model;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    
    private Long id;
    
    private String username;
    
    private String email;
    
    private LocalDateTime createDateTime;
    
    private UserRoleEnum role;
    
    @Builder.Default
    private boolean isDisabled = false;
    
    @Builder.Default
    private boolean isLocked = false;
    
    private String lockedReason;
    
    @Builder.Default
    private boolean isBanned = false;
    
    private String bannedReason;
}
