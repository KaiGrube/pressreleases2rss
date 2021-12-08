package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class Client {

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    static {
        logger.setLevel(Level.ALL);
    }

    private int delayInMillis = 3000;
    private String USER_DIR = System.getProperty("user.dir");
    private String CACHE_DIR = USER_DIR + "/cache/";

    public int getDelayInMillis() {
        return delayInMillis;
    }

    public void setDelayInMillis(int delayInMillis) {
        this.delayInMillis = delayInMillis;
    }

    public String getUSER_DIR() {
        return USER_DIR;
    }

    public void setUSER_DIR(String USER_DIR) {
        this.USER_DIR = USER_DIR;
    }

    public String getCACHE_DIR() {
        return CACHE_DIR;
    }

    public void setCACHE_DIR(String CACHE_DIR) {
        this.CACHE_DIR = CACHE_DIR;
    }

    abstract String fetchFromWeb(String url);

    public String fetch(String url) {
        String content = "";
        try {
            Path path = Paths.get(CACHE_DIR);
            if (Files.notExists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(evaluateFilename(url));
            if (Files.exists(path)) {
                logger.info("Getting content from cache: " + path);
                content = new String(Files.readAllBytes(path));
            } else {
                logger.info("Getting content from web: " + url);
                content = fetchFromWeb(url);
                PrintWriter printWriter = new PrintWriter(evaluateFilename(url));
                printWriter.print(content);
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private String evaluateFilename(String url) {
        String filename = url.startsWith("https") ? url.substring(8) : url.substring(7);
        filename = filename.replace("/", "_");
        filename = CACHE_DIR + filename;
        filename = filename.substring(0, Math.min(filename.length(), 200));
        return filename;
    }
}
