package com.djccnt15.spring_board.domain.user.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRecoveryForm {
    
    @NotBlank(message = "user ID is essential and must contain at least one non-whitespace character")
    private String username;
    
    @NotBlank(message = "Email is essential and must contain at least one non-whitespace character")
    private String email;
}
