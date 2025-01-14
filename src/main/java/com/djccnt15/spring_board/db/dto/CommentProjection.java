package com.djccnt15.spring_board.db.dto;

import java.time.LocalDateTime;

public interface CommentProjection {
    
    Long getId();
    
    LocalDateTime getCreatedDatetime();
    
    LocalDateTime getUpdatedDatetime();
    
    String getUsername();
    
    Long getUserId();
    
    String getContent();
    
    Integer getVersion();
    
    Long getVoteCount();
}
