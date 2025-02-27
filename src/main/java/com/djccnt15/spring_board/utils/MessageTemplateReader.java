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
    
    private String recoverMailTemplate;
    
    @PostConstruct
    public void init() throws IOException {
        recoverMailTemplate = getMailTemplate("user-recover-mail");
    }
    
    private String getMailTemplate(String fileName) throws IOException {
        var filePath = "classpath:templates/%s.html".formatted(fileName);
        var resource = resourceLoader.getResource(filePath);
        try (var inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
