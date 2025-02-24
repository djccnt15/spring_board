package com.djccnt15.spring_board.config.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@EnableRedisRepositories(basePackages = {"com.djccnt15.spring_board.cache.repository"})
public class RedisConfig {
    
    @Value("${spring.redis.time-to-live}")
    private long ttl;
    
    /**
     * configure JSON serialization for redis caching
     * @return serializer
     */
    @Bean
    public ObjectMapper objectMapper() {
        var typeValidator = BasicPolymorphicTypeValidator.builder()
            .allowIfBaseType(Object.class)  // allow every type of class
            .build();
        
        return new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)  // prettify JSON
            // include class type info for deserialize
            .activateDefaultTyping(
                typeValidator,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
            )
            // disable serialize data as Timestamp
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // ignore unknown-include LocalDateTime- properties not support error
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // support deserialize LocalDateTime
            .registerModule(new JavaTimeModule())
            ;
    }
    
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        var cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMillis(ttl))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper())))
            ;
        
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(cacheConfiguration)
            .build();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        var serializer = new GenericJackson2JsonRedisSerializer(objectMapper());
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
