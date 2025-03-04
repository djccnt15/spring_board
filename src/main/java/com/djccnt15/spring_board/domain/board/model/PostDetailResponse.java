package com.djccnt15.spring_board.domain.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor  // constructor for caching
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
    
    private int version;
    
    private int viewCount;
    
    private int commentCount;
    
    private int voteCount;
    
    private int totalCommentPages;
    
    private List<CommentResponse> commentList;
}
