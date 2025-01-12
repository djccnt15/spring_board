package com.djccnt15.spring_board.db.dto;

import java.time.LocalDateTime;

public interface PostMinimalProjection {
    
    Long getId();
    
    LocalDateTime getCreatedDatetime();
    
    String getTitle();
}
