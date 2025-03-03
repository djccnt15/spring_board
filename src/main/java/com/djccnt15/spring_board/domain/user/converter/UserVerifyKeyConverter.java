package com.djccnt15.spring_board.domain.user.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.cache.entity.UserVerifyKeyCache;
import com.djccnt15.spring_board.domain.user.model.UserVerifyKey;

@Converter
public class UserVerifyKeyConverter {
    
    public UserVerifyKeyCache toCache(UserVerifyKey key) {
        return UserVerifyKeyCache.builder()
            .ttl(key.getTtl())
            .id(key.getId())
            .key(key.getKey())
            .build();
    }
}
