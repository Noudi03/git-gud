package com.app.gitgud.service;

import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import org.commonmark.parser.Parser;


@Service
public class MarkdownService {
    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownService() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    public String toHtml(String markdown) {
        if (markdown == null || markdown.isBlank()) {
            return "";
        }
        return renderer.render(parser.parse(markdown));
    }

}
