package doughawkes.fmserver.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

import doughawkes.fmserver.services.FillService;
import doughawkes.fmserver.services.request.FillRequest;
import doughawkes.fmserver.services.result.ErrorMessage;
import doughawkes.fmserver.services.result.FillResult;

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

            Gson gson = new Gson();
            String respData = "";
            if (fillInstructions.length < 3) {
                String message = "Username required in a fill request: i.e. /fill/Bob or /fill/bob/3";
                ErrorMessage errorMessage = new ErrorMessage(message);
                respData = gson.toJson(errorMessage);
            }
            if (fillInstructions.length > 4) {
                String message = "Too many fill instructions. Just a username and optionally the "
                                  + "number of generations to fill, i.e. /fill/Bob or /fill/Bob/3";
                ErrorMessage errorMessage = new ErrorMessage(message);
                respData = gson.toJson(errorMessage);
            }
            else {
                // The first item (0) will be an empty String, the second (1) will be "fill"
                // so I skip to the 3rd (2) & 4th (3)
                String userName = fillInstructions[2];
                int generations;
                if (fillInstructions.length == 4) {
                    generations = Integer.parseInt(fillInstructions[3]);
                }
                else generations = 4;

                FillRequest fillRequest = new FillRequest(userName, generations);
                FillService fillService = new FillService();
                FillResult fillResult = fillService.fill(fillRequest);
            }



            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            exchange.getResponseBody().close();

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }


    }



}
