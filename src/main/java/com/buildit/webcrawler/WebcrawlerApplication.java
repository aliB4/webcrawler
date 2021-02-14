package com.buildit.webcrawler;


import com.buildit.webcrawler.crawler.WebCrawler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebcrawlerApplication {


    public static void main(String[] args) {

        SpringApplication.run(WebcrawlerApplication.class, args);

        WebCrawler crawler = new WebCrawler();
//        hardcoded for purpose of exercise - but can pass in with args
        String defaultUrl = "http://wiprodigital.com";
        crawler.run(args != null && args.length > 0 ? args[0] : defaultUrl);

    }


}
