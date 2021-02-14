package com.buildit.webcrawler.parser;


import com.buildit.webcrawler.domain.WebPage;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;



@Component
public class HtmlParser {


    private HtmlParser(){}

    @Autowired
    private HtmlParserHelper htmlParserHelper;

   public static Document retrieveDocumentFromUrl(String url) {
        return HtmlParserHelper.loadUrl(url);
   }

    public static WebPage retrieveWebPageFromUrl(String url) {
        Set<String> wiproUrls = new HashSet<>();
        Set<String> otherUrls = new HashSet<>();
        Set<String> staticContent = new HashSet<>();

//       System.out.println("URL " + url);
       Document doc = retrieveDocumentFromUrl(url);
        Elements links = doc.select("a");
        Elements imgs = doc.select("img");

        if(links != null) {
            List<String> allUrls = links.stream().map(l -> l.attr("href")).collect(Collectors.toList());
             wiproUrls = allUrls.stream().filter(u -> u.contains("https://wiprodigital.com")).collect(Collectors.toSet());
            otherUrls = allUrls.stream().filter(u -> !u.contains("https://wiprodigital.com")).collect(Collectors.toSet());

        }
        staticContent = imgs.stream().map(l -> l.attr("src")).collect(Collectors.toSet());
       return new WebPage(url, wiproUrls, otherUrls, staticContent);
    }

    public static HashSet<WebPage> retrieveRecursiveSetOfWebPagesFromUrl(String url, HashSet<WebPage> webPages) {
        Predicate<WebPage> predicate = wp -> wp.getUrl().equals(url);
        Iterator<WebPage> webPageIterator = webPages.iterator();
        while(webPageIterator.hasNext()){
            if(predicate.test(webPageIterator.next())){
                return webPages;
            }
        }

       WebPage webPage = retrieveWebPageFromUrl(url);
       webPages.add(webPage);

       webPage.getRelatedLinks().forEach(link -> webPages.addAll(retrieveRecursiveSetOfWebPagesFromUrl(link, webPages)));

       return webPages;
    }
}
