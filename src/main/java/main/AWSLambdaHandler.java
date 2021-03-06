package main;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import rss.RSSFeed;
import scraper.PressScraper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AWSLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private boolean isApiKeyValid(APIGatewayProxyRequestEvent requestEvent) {
        Optional<String> requestApiKey = Optional.ofNullable(requestEvent.getQueryStringParameters().get("API_KEY"));
        if (requestApiKey.isEmpty()) return false;
        Optional<String> validApiKey = Optional.ofNullable(System.getenv("API_KEY"));
        if (validApiKey.isEmpty()) return false;
        return requestApiKey.get().equals(validApiKey.get());
    }

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent requestEvent, final Context context) {
        LambdaLogger logger = context.getLogger();
        Map<String, String> headers = new HashMap<>();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(headers);
        if (!isApiKeyValid(requestEvent)) {
            headers.put("Content-Type", "text/html");
            headers.put("X-Custom-Header", "text/html");
            logger.log("Error 401 - Unauthorized request");
            return  response
                    .withStatusCode(401)
                    .withBody("<html>Error 401 - Unauthorized</html>");
        }

        RSSFeed rssFeed = new PressScraper().scrape();
        Optional<String> xmlContent = Marshaller.marshallToString(rssFeed);
        if (xmlContent.isEmpty()) {
            headers.put("Content-Type", "text/html");
            headers.put("X-Custom-Header", "text/html");
            return response
                    .withStatusCode(500)
                    .withBody("<html>Error 500 - Internal Server Error</html>");
        } else {
            headers.put("Content-Type", "text/xml");
            headers.put("X-Custom-Header", "text/xml");
            return response
                    .withStatusCode(200)
                    .withBody(xmlContent.get());
        }
    }
}