package com.djccnt15.spring_board.domain.user.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserPostResponse {
    
    private Long id;
    
    private LocalDateTime createdDateTime;
    
    private LocalDateTime updatedDateTime;
    
    private String mainCategory;

    private String subCategory;
    
    private String title;
    
    private String content;
    
    private int version;
    
    private int viewCount;
    
    private int commentCount;
    
    private int voteCount;
}
