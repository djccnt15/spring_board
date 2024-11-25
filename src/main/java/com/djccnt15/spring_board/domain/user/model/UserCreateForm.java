package com.djccnt15.spring_board.domain.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateForm {
    
    @Size(min = 3, max = 25)
    @NotEmpty(message = "user id is essential")
    private String username;
    
    @NotEmpty(message = "password is essential")
    private String password1;
    
    @NotEmpty(message = "password validation is essential")
    private String password2;
    
    @NotEmpty(message = "email is essential")
    @Email
    private String email;
}
