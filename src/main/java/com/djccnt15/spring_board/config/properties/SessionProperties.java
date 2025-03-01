package com.djccnt15.spring_board.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.session.remember")
public class SessionProperties {
    
    private final int tokenValidSec;
    private final String key;
}
