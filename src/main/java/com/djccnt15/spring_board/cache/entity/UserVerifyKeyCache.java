package com.djccnt15.spring_board.cache.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@RedisHash(value = "UserVerifyKey")
@Builder
public class UserVerifyKeyCache {
    
    @TimeToLive
    private Long ttl;

    @Id
    private Long id;
    
    private String key;
}
