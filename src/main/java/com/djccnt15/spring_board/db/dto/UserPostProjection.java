package com.djccnt15.spring_board.db.dto;

import java.time.LocalDateTime;

public interface UserPostProjection {
    
    Long getId();
    
    LocalDateTime getCreatedDatetime();
    
    LocalDateTime getUpdatedDatetime();
    
    String getMainCategory();
    
    String getSubCategory();
    
    String getTitle();
    
    String getContent();
    
    Integer getViewCount();
    
    Integer getCommentCount();
    
    Integer getVoteCount();
}
