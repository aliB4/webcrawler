package com.buildit.webcrawler.domain;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class WebPageTests {


    @Test
    public void assignWebPageFieldsSuccess() {
        String URL = "https://wiprodigital.com";

        Set<String> relatedLinks = new HashSet<>();
        relatedLinks.add("https://wiprodigital.com/what-we-are");

        Set<String> externalLinks = new HashSet<>();
        externalLinks.add("www.google.co.uk");

        Set<String> staticContent = new HashSet<>();
        staticContent.add("staticContent");

        WebPage webPage = new WebPage(URL, relatedLinks, externalLinks, staticContent);

        assertThat(webPage).isNotNull();
        assertThat(webPage.getUrl()).isEqualTo(URL);
        assertThat(webPage.getRelatedLinks().size()).isEqualTo(1);
        assertThat(webPage.getExternalLinks().size()).isEqualTo(1);
        assertThat(webPage.getStaticContent().size()).isEqualTo(1);
    }
}
