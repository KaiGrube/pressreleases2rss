package main;

import rss.Channel;
import scraper.PressScraper;

import java.io.File;
import java.nio.file.FileSystems;

public class LocalStarter {

    private static final String PATH_NAME = System.getProperty("user.dir")
            + FileSystems.getDefault().getSeparator()
            + "channel.xml";

    public static void main(String[] args) {
        Channel channel = new PressScraper().scrape();
        Marshaller.marshallToString(channel).ifPresent(System.out::println);
        Marshaller.marshallToFile(channel, new File(PATH_NAME));
    }
}