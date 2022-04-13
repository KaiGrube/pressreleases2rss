package main;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import rss.Channel;
import scraper.PressScraper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AWSLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {

        Channel channel = new PressScraper().scrape();
        Optional<String> xmlContent = Marshaller.marshallToString(channel);

        Map<String, String> headers = new HashMap<>();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(headers);
        if (xmlContent.isEmpty()) {
            headers.put("Content-Type", "text/html");
            headers.put("X-Custom-Header", "text/html");
            return response
                    .withStatusCode(500)
                    .withBody("<html>server error</html>");
        } else {
            headers.put("Content-Type", "text/xml");
            headers.put("X-Custom-Header", "text/xml");
            return response
                    .withStatusCode(200)
                    .withBody(xmlContent.get());
        }
    }
}


//public class AWSLambdaHandler implements RequestHandler<Map<String, String>, String> {
//
//    public String handleRequest(Map<String, String> event, Context context) {
//        // log execution details, process event
//        LambdaLogger logger = context.getLogger();
//        logger.log("EVENT TYPE: " + event.getClass().toString());
//        Channel channel = new PressScraper().scrape();
//        Optional<String> xmlContent = Marshaller.marshallToString(channel);
//        return xmlContent.orElse("<xml>Error</xml> "); // todo: change error handling
//    }
//}