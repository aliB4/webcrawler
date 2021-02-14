package com.buildit.webcrawler.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebPage {

    private String url;
    private Set<String> relatedLinks;
    private Set<String> externalLinks;
    private Set<String> staticContent;

    public void printWebPageStructure() {
        System.out.println();
        System.out.println("URL: " + url);
        System.out.println();
        System.out.println("    RELATED LINKS:");
        relatedLinks.forEach( rl -> {
            System.out.println("    " + rl);
        });
        System.out.println("     EXTERNAL LINKS:");
        externalLinks.forEach( el -> {
            System.out.println("        " + el);
        });

        System.out.println("         STATIC CONTENT:");
        staticContent.forEach( sc -> {
            System.out.println("            " + sc);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebPage)) return false;
        WebPage webPage = (WebPage) o;
        return url.equals(webPage.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
