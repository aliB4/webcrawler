package com.buildit.webcrawler.crawler;

import com.buildit.webcrawler.domain.WebPage;
import com.buildit.webcrawler.parser.HtmlParser;


import java.util.HashSet;

public class WebCrawler {


    public void run(String testUrl) {
        HashSet<WebPage> webPages = new HashSet<>();

        System.out.println("************************* " + "Start Crawling " + testUrl + " **************************");
        webPages = HtmlParser.retrieveRecursiveSetOfWebPagesFromUrl(testUrl, webPages);

        System.out.println("************************* " + "End Crawling And Print " + testUrl + " **************************");

        webPages.forEach( webPage -> webPage.printWebPageStructure());
    }
}
