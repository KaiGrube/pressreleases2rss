package starter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import scraper.PressScraper;

import java.util.Map;

public class AWSLambdaHandler implements RequestHandler<Map<String, String>, String> {

    public String handleRequest(Map<String, String> event, Context context) {
        // log execution details, process event
        LambdaLogger logger = context.getLogger();
        logger.log("EVENT TYPE: " + event.getClass().toString());
        PressScraper pressemitteilungen = new PressScraper();
        String content = pressemitteilungen.scrape().toXml();
        return content;
    }
}