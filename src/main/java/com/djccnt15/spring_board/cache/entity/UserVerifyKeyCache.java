package com.djccnt15.spring_board.cache.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash(value = "UserVerifyKey")
@Builder
public class UserVerifyKeyCache {
    
    @TimeToLive
    private Long ttl;

    @Indexed
    private Long id;
    
    private String key;
}
