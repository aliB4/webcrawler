package com.buildit.webcrawler.crawler;

import com.buildit.webcrawler.domain.WebPage;
import com.buildit.webcrawler.parser.HtmlParser;

import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;

public class WebCrawlerTests {

    private static final String TEST_URL = "http://wiprodigital.com";

    private WebCrawler webCrawler;

    @Test
    public void testWebCrawlerRuns() {

        HashSet<WebPage> returnedWebPages = new HashSet<>();
        try (MockedStatic<HtmlParser> htmlParserMock = Mockito.mockStatic(HtmlParser.class)) {
            htmlParserMock.when(() -> HtmlParser.retrieveRecursiveSetOfWebPagesFromUrl(eq(TEST_URL), isA(HashSet.class))).thenReturn(returnedWebPages);

            webCrawler = new WebCrawler();
            webCrawler.run(TEST_URL);
        }
    }
}
