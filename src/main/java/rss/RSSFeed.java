package rss;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class RSSFeed {

    private Channel channel;

    public RSSFeed() {}

    public RSSFeed(Channel channel) {
        this.channel = channel;
    }
}
