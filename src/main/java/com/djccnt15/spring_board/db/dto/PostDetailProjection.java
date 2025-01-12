package com.djccnt15.spring_board.db.dto;

import java.time.LocalDateTime;

public interface PostDetailProjection {
    
    Long getId();
    
    LocalDateTime getCreatedDatetime();
    
    LocalDateTime getUpdatedDatetime();
    
    String getUsername();
    
    String getTitle();
    
    String getContent();
    
    Long getCommentCount();
    
    Long getVoteCount();
}
