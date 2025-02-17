package com.djccnt15.spring_board.domain.user.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserCommentResponse {
    
    private Long id;
    
    private Long postId;
    
    private String category;
    
    private LocalDateTime createdDateTime;
    
    private LocalDateTime updatedDateTime;
    
    private String content;
    
    private int version;
    
    private int voteCount;
}
