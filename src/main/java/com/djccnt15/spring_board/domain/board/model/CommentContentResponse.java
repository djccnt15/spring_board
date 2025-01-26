package com.djccnt15.spring_board.domain.board.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentContentResponse {
    
    private Long id;
    
    private LocalDateTime createdDateTime;
    
    private String content;
}
