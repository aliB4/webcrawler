package com.buildit.webcrawler.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlParserHelper {

    private HtmlParserHelper(){}

    public static Document loadUrl(String testUrl) {
        Document doc = null;
        try {
            doc = Jsoup.connect(testUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }
}
