package com.djccnt15.spring_board.utils;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Configuration
@RequiredArgsConstructor
public class MessageTemplateReader {
    
    @Getter(value = AccessLevel.NONE)  // exclude attribute from lombok getter
    private final ResourceLoader resourceLoader;
    private String mailingTemplate;
    
    @PostConstruct
    public void init() throws IOException {
        var resource = resourceLoader.getResource("classpath:templates/user-recover-mail.html");
        try (var inputStream = resource.getInputStream()) {
            mailingTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
