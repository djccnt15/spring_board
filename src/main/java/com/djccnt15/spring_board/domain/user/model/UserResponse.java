package com.djccnt15.spring_board.domain.user.model;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
    @Builder.Default
    private boolean isBanned = false;
}
