package com.djccnt15.spring_board.domain.board.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDetailResponse {
    
    private Long id;
    
    private LocalDateTime createdDateTime;
    
    private LocalDateTime updatedDateTime;
    
    private String author;
    
    private String title;
    
    private String content;
    
    private Integer version;
    
    private Long commentCount;
    
    private Long voteCount;
}
