package com.djccnt15.spring_board.domain.board.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostDetailResponse {
    
    private Long id;
    
    private LocalDateTime createdDateTime;
    
    private LocalDateTime updatedDateTime;
    
    private String author;
    
    private Long authorId;
    
    private String category;
    
    private String title;
    
    private String content;
    
    private Integer version;
    
    private Long commentCount;
    
    private Long voteCount;
    
    private List<CommentResponse> commentList;
}
