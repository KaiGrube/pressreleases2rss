package rss;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

// RSS2.0-Specification, see https://www.rssboard.org/rss-specification

public class RSSChannel extends Object {
    // Required channel elements
    private final String title;
    private final String link;
    private final String description;

    private final List<RSSItem> rssItems = new ArrayList<>();

    public RSSChannel(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public void addItem(RSSItem RSSItem) {
        rssItems.add(RSSItem);
    }

    public void addItems(List<RSSItem> rssItems) {
        this.rssItems.addAll(rssItems);
    }
    
    public String toXml() {
        String header =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<rss version=\"2.0\">\n" +
                        "<channel>\n" +
                        "<title>" + StringEscapeUtils.escapeXml11(title) + "</title>\n" +
                        "<link>" + StringEscapeUtils.escapeXml11(link) + "</link>\n" +
                        "<description>" + StringEscapeUtils.escapeXml11(description) + "</description>\n";

        String footer =
                "</channel>\n" +
                        "</rss>\n";

        StringBuilder xmlDocument = new StringBuilder(header);
        rssItems.forEach(item -> xmlDocument.append(item.toXml()));
        xmlDocument.append(footer);
        return xmlDocument.toString();
    }
}
