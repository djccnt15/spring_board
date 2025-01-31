package com.djccnt15.spring_board.domain.board.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentCreateRequest {
    
    @NotEmpty(message = "CONTENT is essential")
    private String content;
}
