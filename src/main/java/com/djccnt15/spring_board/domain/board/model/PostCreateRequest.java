package com.djccnt15.spring_board.domain.board.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreateRequest {
    
    @NotBlank
    private String title;
    
    private String content;
}
