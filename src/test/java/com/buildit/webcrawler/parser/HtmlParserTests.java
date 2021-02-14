package com.buildit.webcrawler.parser;

import com.buildit.webcrawler.domain.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.HashSet;

import static junit.framework.TestCase.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HtmlParserTests {

    private HtmlParser htmlParser;

    @Mock
    private HtmlParserHelper htmlParserHelperMock;

    private static final String TEST_URL = "http://wiprodigital.com";
    private static final String TEST_WHO_WE_ARE_URL = "https://wiprodigital.com/who-we-are/";
    private static final String TEST_WHAT_WE_DO_URL = "https://wiprodigital.com/what-we-do/";
    private static final String TEST_PEOPLE_URL = "https://wiprodigital.com/people/";
    private static final String TEST_TECHNOLOGY_URL = "https://wiprodigital.com/technology/";

    private Document wiproRootDoc;

    private Document wiproWhoWeAreDoc;

    private Document wiproWhatWeDoDoc;

    private Document wiproPeopleDoc;

    private Document wiproTechnologyDoc;

    private Document wiproDuplicateLinksDoc;

    @Before
    public void setUp(){

        String html = "<html><head>Something Here<img class=\"wdhome-hdrlogo-img\" src=\"https://s17776.pcdn.co/wp-content/themes/wiprodigital/images/logo.png\" alt=\"Digital Transformation - Wipro Digital\" title=\"Wipro Digital\"></head><body><img class='wdhome-hdrlogo-img' src='https://wiprodigital/images/logo2.png'><img class='wdhome-hdrlogo-img' src='https://wiprodigital/images/anotherImage.png'><a href='https://wiprodigital.com/who-we-are/'/><a href='http://google.co.uk'/><a href='https://wiprodigital.com/what-we-do/'/><a href='http://bbc.co.uk'/></body></html>";
        wiproRootDoc = Jsoup.parse(html);

        String htmlWhoWeAre = "<html><head>Wipro Who We Are</head><body><a href='http://someoexternalsite.co.uk'/></body></html>";
        wiproWhoWeAreDoc = Jsoup.parse(htmlWhoWeAre);

        String htmlWhatWeDo = "<html><head>Wipro What We Do</head><body><a href='https://wiprodigital.com/technology/'/><a href='https://wiprodigital.com/people/'/></body></html>";
        wiproWhatWeDoDoc = Jsoup.parse(htmlWhatWeDo);

        String htmlPeople = "<html><head>Wipro Leadership</head><body>No Links Here Scenario</body></html>";
        wiproPeopleDoc = Jsoup.parse(htmlPeople);

        String htmlTechnology = "<html><head>Wipro Technology</head><body>Link already used on previous webpage Test Scenario<a href='http://twitter.com'/><a href='https://wiprodigital.com/people/'/></body></html>";
        wiproTechnologyDoc = Jsoup.parse(htmlTechnology);


        String htmlDuplicateLinks = "<html><head>Something Here<img  src='https://wiprodigital/images/anotherImage.png'/><img  src='https://wiprodigital/images/anotherImage.png'><a href='https://wiprodigital.com/who-we-are/'/><a href='http://google.co.uk'/><a href='https://wiprodigital.com/who-we-are/'/><a href='http://google.co.uk'/></body></html>";
        wiproDuplicateLinksDoc = Jsoup.parse(htmlDuplicateLinks);
    }

    @Test
    public void testReturnsDocumentSuccess() {

        try (MockedStatic<HtmlParserHelper> htmlParserHelperMock = Mockito.mockStatic(HtmlParserHelper.class)) {
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_URL))).thenReturn(wiproRootDoc);

            assertEquals(wiproRootDoc, HtmlParser.retrieveDocumentFromUrl(TEST_URL));

        }
    }

    @Test
    public void testReturnsWebPageObjectContainingLinks() {

        try (MockedStatic<HtmlParserHelper> htmlParserHelperMock = Mockito.mockStatic(HtmlParserHelper.class)) {
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_URL))).thenReturn(wiproRootDoc);

            WebPage webPage = HtmlParser.retrieveWebPageFromUrl(TEST_URL);
            assertEquals(TEST_URL, webPage.getUrl());
            assertEquals(2, webPage.getRelatedLinks().size());
            assertEquals(2, webPage.getExternalLinks().size());
            assertTrue(webPage.getExternalLinks().contains("http://google.co.uk"));
            assertTrue(webPage.getRelatedLinks().contains("https://wiprodigital.com/who-we-are/"));
        }
    }

    @Test
    public void testReturnsWebPageWithStaticContent() {

        try (MockedStatic<HtmlParserHelper> htmlParserHelperMock = Mockito.mockStatic(HtmlParserHelper.class)) {
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_URL))).thenReturn(wiproRootDoc);

            WebPage webPage = HtmlParser.retrieveWebPageFromUrl(TEST_URL);
            assertEquals(TEST_URL, webPage.getUrl());
            assertEquals(3, webPage.getStaticContent().size());
            assertTrue(webPage.getStaticContent().contains("https://wiprodigital/images/logo2.png"));

        }
    }

    @Test
    public void testWebPageDoesntContainDuplicateLinks() {

            try (MockedStatic<HtmlParserHelper> htmlParserHelperMock = Mockito.mockStatic(HtmlParserHelper.class)) {
                htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_URL))).thenReturn(wiproDuplicateLinksDoc);

                WebPage webPage = HtmlParser.retrieveWebPageFromUrl(TEST_URL);
                assertEquals(TEST_URL, webPage.getUrl());
                assertEquals(1, webPage.getRelatedLinks().size());
                assertEquals(1, webPage.getExternalLinks().size());
                assertEquals(1, webPage.getStaticContent().size());

            }

    }

    @Test
    public void testReturnsListOfUniqueInternalWebPagesFollowingRecursion() {

        try (MockedStatic<HtmlParserHelper> htmlParserHelperMock = Mockito.mockStatic(HtmlParserHelper.class)) {
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_URL))).thenReturn(wiproRootDoc);
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_WHO_WE_ARE_URL))).thenReturn(wiproWhoWeAreDoc);
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_WHAT_WE_DO_URL))).thenReturn(wiproWhatWeDoDoc);
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_TECHNOLOGY_URL))).thenReturn(wiproTechnologyDoc);
            htmlParserHelperMock.when(() -> HtmlParserHelper.loadUrl(eq(TEST_PEOPLE_URL))).thenReturn(wiproPeopleDoc);

            HashSet<WebPage> webpages = HtmlParser.retrieveRecursiveSetOfWebPagesFromUrl(TEST_URL, new HashSet<WebPage>());

            assertEquals(5, webpages.size());

        }

    }

//    TODO: Write test case and implement code to hand these cases - they end up in External Link section
//    <a class="wd-navbar-nav-elem-link wd-nav-elem-link wd-currpage-scroll-link" href="#wdwork_partners">Partners</a>

//    @Test
//    public void testHandlesNoLinksFound() {
//
//    }
}
