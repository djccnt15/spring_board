package com.djccnt15.spring_board.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CacheService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * do not use this method for not having TTL setting
     */
    @Deprecated
    public void saveCache(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    public void saveCache(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }
    
    public <T> T getCache(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        return type.cast(value);
    }
    
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }
}
