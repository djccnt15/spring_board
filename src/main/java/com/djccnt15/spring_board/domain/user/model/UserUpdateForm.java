package com.djccnt15.spring_board.domain.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateForm {
    
    @Size(min = 3, max = 25)
    @NotBlank(message = "user id is essential and must contain at least one non-whitespace character")
    private String username;
    
    private String password;
    
    private String password1;
    
    private String password2;
    
    @NotBlank(message = "email is essential and must contain at least one non-whitespace character")
    @Email
    private String email;
}
