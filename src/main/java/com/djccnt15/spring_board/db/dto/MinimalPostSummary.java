package com.djccnt15.spring_board.db.dto;

import java.time.LocalDateTime;

public interface MinimalPostSummary {
    
    Long getId();
    
    LocalDateTime getCreatedDatetime();
    
    String getTitle();
}
