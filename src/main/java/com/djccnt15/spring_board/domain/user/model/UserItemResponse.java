package com.djccnt15.spring_board.domain.user.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class UserItemResponse {
    
    private Long id;
    
    private LocalDateTime createdDateTime;
    
    private LocalDateTime updatedDateTime;
    
    private String content;
    
    private Integer version;
    
    private Integer voteCount;
}
