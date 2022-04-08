package rss;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
// todo: Escape HTML!
// import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

// RSS2.0-Specification, see https://www.rssboard.org/rss-specification

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Channel {
    String link;    // todo: check rules for visibility modifiers when using jaxb
    String title;
    String description;
    @XmlElement(name="item")
    List<Item> items = new ArrayList<Item>();

    public Channel() {}

    public Channel(String title, String link, String description, List<Item> items) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.items.addAll(items);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                '}';
    }
}
