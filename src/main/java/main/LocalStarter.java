package main;

import rss.RSSFeed;
import scraper.PressScraper;

import java.io.File;
import java.nio.file.FileSystems;

public class LocalStarter {

    private static final String PATH_NAME = System.getProperty("user.dir")
            + FileSystems.getDefault().getSeparator()
            + "pressreleases-example-output.xml";

    public static void main(String[] args) {
        RSSFeed rssFeed = new PressScraper().scrape();
        Marshaller.marshallToString(rssFeed).ifPresent(System.out::println);
        Marshaller.marshallToFile(rssFeed, new File(PATH_NAME));
    }
}