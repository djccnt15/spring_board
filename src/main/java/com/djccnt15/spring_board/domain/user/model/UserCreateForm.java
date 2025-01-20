package com.djccnt15.spring_board.domain.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateForm {
    
    @Size(min = 3, max = 25)
    @NotBlank(message = "user ID is essential and must contain at least one non-whitespace character")
    private String username;
    
    @NotBlank(message = "password is essential and must contain at least one non-whitespace character")
    private String password1;
    
    @NotBlank(message = "password validation is essential and must contain at least one non-whitespace character")
    private String password2;
    
    @NotBlank(message = "Email is essential and must contain at least one non-whitespace character")
    @Email
    private String email;
}
