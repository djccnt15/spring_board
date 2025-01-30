package com.djccnt15.spring_board.db.dto;

import java.time.LocalDateTime;

public interface UserCommentProjection {
    
    Long getId();
    
    Long getPostId();
    
    String getCategory();
    
    LocalDateTime getCreatedDatetime();
    
    LocalDateTime getUpdatedDatetime();
    
    String getContent();
    
    Integer getVersion();
    
    Integer getVoteCount();
}
