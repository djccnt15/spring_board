package com.djccnt15.spring_board.config.properties;

import com.djccnt15.spring_board.annotations.Property;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Property
@Getter
@Setter(value = AccessLevel.PACKAGE)  // disallow user to use setter of fields
@ConfigurationProperties(prefix = "app.session.remember")
public class SessionProperties {
    
    private int tokenValidSec;
    private String key;
}
