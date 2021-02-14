# How to Run

1. Run main method in WebcrawlerApplication from IDE

2. You can run all the tests too

or from Root directory in command line

1. mvn package
2. java -jar target/webcrawler-0.0.1SNAPSHOT.jar

#### Thought process.

1. Create test HTMLParserTests - first test just to return Document. Refactor code to use HtmlParserHelper to be able to mock the JSoup part.
2. Add test and code to return WebPage with lists for internal/external links having parsed Document.
3. Add test and code to recurse through a tree of webpages - ensure it doesn't visit the same page more then once
4. Add test and code to find static content
5. Add print out ability to see the WebPage to console
6. Add test and modify code to use Set rather then List to prevent duplicating links

### TODO IF MORE TIME:

 TODO: Write test case and implement code to hand these cases - they end up in External Link section
<a class="wd-navbar-nav-elem-link wd-nav-elem-link wd-currpage-scroll-link" href="#wdwork_partners">Partners</a>

validation of url

handle different http responses if page not found etc

Limit depth of search (make configurable) - as could be searching a long time

Configuration perhaps to allow flexibility for what you are crawling for

Implement better response/output depending on requirements

Consider speed/efficiency of the search if can make improvements

Perhaps look at any other html parsers for evaluation with JSoup

Consider scaling/running multiple crawlers at once

add logging


