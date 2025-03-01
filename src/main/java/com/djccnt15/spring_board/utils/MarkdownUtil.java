package com.djccnt15.spring_board.utils;

import com.djccnt15.spring_board.annotations.Utility;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Slf4j
@Utility
public class MarkdownUtil {
    
    private Parser parser;
    private HtmlRenderer renderer;
    
    @PostConstruct
    private void init() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }
    
    public String renderMarkdown(String markdown) {
        if (markdown == null) {
            return "";
        }
        var document = parser.parse(markdown);
        return renderer.render(document);
    }
}
