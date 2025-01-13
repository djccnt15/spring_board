package com.djccnt15.spring_board.domain.board.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequest {
    
    @NotBlank(message = "category is essential")
    private String category;
    
    @NotBlank(message = "title must not be null and must contain at least one non-whitespace character")
    private String title;
    
    private String content;
}
