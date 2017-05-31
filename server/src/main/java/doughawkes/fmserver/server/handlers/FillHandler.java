package doughawkes.fmserver.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by yo on 5/31/17.
 */

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("fillhandler");
        try {

            String theURI = exchange.getRequestURI().toString();
            String[] fillInstructions = theURI.split("/");

            // The first item (0) will be an empty String, the second (1) will be "fill"
            // so I skip to the 3rd (2) & 4th (3)
            String userName = fillInstructions[2];
            int generationsToFill = Integer.parseInt(fillInstructions[3]);


            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            exchange.getResponseBody().close();

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }


    }



}
