package main;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import rss.Channel;
import scraper.PressScraper;

import java.util.Map;
import java.util.Optional;

public class AWSLambdaHandler implements RequestHandler<Map<String, String>, String> {

    public String handleRequest(Map<String, String> event, Context context) {
        // log execution details, process event
        LambdaLogger logger = context.getLogger();
        logger.log("EVENT TYPE: " + event.getClass().toString());
        Channel channel = new PressScraper().scrape();
        Optional<String> xmlContent = Marshaller.marshallToString(channel);
        return xmlContent.orElse("<xml>Error</xml> "); // todo: change error handling
    }
}