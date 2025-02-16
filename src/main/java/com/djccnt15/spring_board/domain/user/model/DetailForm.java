package com.djccnt15.spring_board.domain.user.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DetailForm {
    
    @NotBlank(message = "reason is essential")
    private String detail;
}
