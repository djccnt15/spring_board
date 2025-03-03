package com.djccnt15.spring_board.domain.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVerifyKey {
    
    private Long ttl;
    
    private Long id;
    
    private String key;
}
