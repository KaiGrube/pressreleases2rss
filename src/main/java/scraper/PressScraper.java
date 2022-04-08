package scraper;

import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rss.Channel;
import rss.Item;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// scrapes press releases of senate of hamburg
public class PressScraper {

    private static final String BASE_URL = "https://www.hamburg.de";

    // required channel elements
    private static final String LINK = BASE_URL + "/pressemeldungen";
    private static final String TITLE = "Pressemitteilungen der Senatskanzlei Hamburg";
    private static final String DESCRIPTION = "https://www.hamburg.de/pressemeldungen";
    private static final String userAgent =
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

    public String fetchFromWeb(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(userAgent);
            Document document = connection.get();
            return document.html();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Channel scrape() {
        String content = fetchFromWeb(LINK);
        Document document = Jsoup.parse(content);
        List<Item> items = new ArrayList<>();
        Element pressReleasesCity = document.getElementsByClass("component").first();
        Elements articles = pressReleasesCity.getElementsByClass("col-xs-12 col-md-12 teaser teaser-thumb teaser-thumb-fhh ");

        for (Element article : articles) {
            // title = kicker + headline + source
            String kicker = "kein Kicker vorhanden";
            Element kickerElement = article.getElementsByClass("teaser-kicker ").first();
            if (kickerElement != null) kicker = kickerElement.text();
            String headline = "keine Überschrift vorhanden";
            Element headlineElement = article.getElementsByClass("teaser-headline ").first();
            if (headlineElement != null) headline = headlineElement.text();
            String source = "keine Quelle vorhanden";
            Element sourceElement = article.getElementsByClass("teaser-source").first();
            if (sourceElement != null) source = sourceElement.text().replace("Quelle:", "").trim();

            // Build title = kicker + headline + source
            String title = String.format("%s - %s [Quelle: %s]", kicker, headline, source);

            // link
            String targetLink = null;
            Element linkElement = article.getElementsByAttribute("data-click-link-target").first();
            if (linkElement != null) targetLink = linkElement.attr("data-click-link-target");

            // image source
            String imageSource = "keine Bildquelle vorhanden";
            Element imageElement = article.getElementsByTag("img").first();
            if (imageElement != null) imageSource = BASE_URL + imageElement.attr("src");

            // description
            String description = "keine Beschreibung vorhanden";
            Element descriptionElement = article.getElementsByClass("teaser-text hidden-sm-down").first();
            if (descriptionElement != null) {
                description = descriptionElement.html();
            }

            // pubDate
            String pubDate = "kein Datum vorhanden";
            Element dateElement = article.getElementsByClass("teaser-text-meta").first();
            if (dateElement != null) {
                pubDate = dateElement.text();
                ZonedDateTime zonedDateTime = LocalDate
                        .parse(pubDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        .atStartOfDay()
                        .atZone(ZoneId.of("Europe/Berlin"));
                pubDate = zonedDateTime.format(DateTimeFormatter.ofPattern("E, dd MMM yyyy kk:mm:ss xxxx"));
            }

            // guid (Global Unique Identifier)
            final String originalString = headline + pubDate + description;
            String guid = DigestUtils.sha256Hex(originalString);

            items.add(new Item(title, targetLink, description, guid, pubDate, source, imageSource));
        }

        return new Channel(TITLE, LINK, DESCRIPTION, items);
    }
}