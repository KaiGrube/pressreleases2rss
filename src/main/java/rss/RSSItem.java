package rss;

import org.apache.commons.text.StringEscapeUtils;

// All elements of an item are optional, however at least one, either title or description must be present!
// https://www.rssboard.org/rss-specification#hrelementsOfLtitemgt
public class RSSItem {
    private final String title;
    private final String link;
    private final String description;
    private final String guid;
    private final String pubDate;
    private final String source;
    private final String imageSource; // no item element of rss 2.0

    public RSSItem(String title, String link, String description, String guid, String pubDate, String source, String imageSource) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.guid = guid;
        this.source = source;
        this.imageSource = imageSource;
    }

    @Override
    public String toString() {
        return "<item>\n" +
                "\t<title>" + StringEscapeUtils.escapeXml11(title) + "</title>\n" +
                "\t<link>" + StringEscapeUtils.escapeXml11(link) + "</link>\n" +
                "\t<description>"
                + StringEscapeUtils.escapeXml11(description)
                + String.format("<br/><br/><![CDATA[<img src=\"%s\"></img>]]>", imageSource)
                + "</description>\n" +
                "\t<guid isPermaLink='false'>" + guid + "</guid>\n" +
                "\t<pubDate>" + StringEscapeUtils.escapeXml11(pubDate) + "</pubDate>\n" +
                "\t<source>" + StringEscapeUtils.escapeXml11(source) + "</source>\n" +
                "</item>\n";
    }
}
