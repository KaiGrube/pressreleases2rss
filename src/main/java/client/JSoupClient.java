package client;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


// public class JSoupClient extends Client {

public class JSoupClient {

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
}
