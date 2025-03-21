package com.djccnt15.spring_board.db.dto;

import java.time.LocalDateTime;

public interface PostDetailProjection {
    
    Long getId();
    
    LocalDateTime getCreatedDatetime();
    
    LocalDateTime getUpdatedDatetime();
    
    String getUsername();
    
    Long getUserId();
    
    String getCategory();
    
    String getTitle();
    
    String getContent();
    
    int getVersion();
    
    int getViewCount();
    
    int getCommentCount();
    
    int getVoteCount();
}
