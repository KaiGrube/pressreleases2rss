package starter;

import scraper.PressScraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LocalStarter {

    private static final String USER_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        PressScraper pressemitteilungen = new PressScraper();
        String channel = pressemitteilungen.scrape();
        System.out.println(channel);
        writeToFile(channel, "/Pressemitteilungen_SenatHH.xml");
    }

    private static void writeToFile(String content, String filename) {
        String absolutePath = USER_DIR + filename;
        try (PrintWriter writer = new PrintWriter(new File(absolutePath))) {
            writer.println(content);
            System.out.println("Wrote rss file to " + absolutePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}