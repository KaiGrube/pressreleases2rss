package rss;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

// All elements of an item are optional, however at least one, either title or description must be present!
// https://www.rssboard.org/rss-specification#hrelementsOfLtitemgt
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    public String title;
    public String link;
    public String description;
    public String guid;
    public String pubDate;
    public String source;
    public String imageSource; // no item element of rss 2.0

    public Item() {};

    public Item(String title, String link, String description, String guid, String pubDate, String source, String imageSource) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.guid = guid;
        this.pubDate = pubDate;
        this.source = source;
        this.imageSource = imageSource;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", guid='" + guid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", source='" + source + '\'' +
                ", imageSource='" + imageSource + '\'' +
                '}';
    }
}
