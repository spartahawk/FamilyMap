package doughawkes.fmserver.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.User;
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
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String theURI = exchange.getRequestURI().toString();
                String[] fillInstructions = theURI.split("/");

                //System.out.println("fill instructions length: " + fillInstructions.length);

                Gson gson = new Gson();
                String respData = "";
                if (fillInstructions.length < 3) {
                    String message = "Username required in a fill request: i.e. /fill/Bob or /fill/Bob/3";
                    HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                    handlerErrorMessage.sendErrorMessage(exchange, message);
                    return;
                }
                if (fillInstructions.length > 4) {
                    String message = "Too many fill instructions. Just a username and optionally the "
                            + "number of generations to fill, i.e. /fill/Bob or /fill/Bob/3";
                    HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                    handlerErrorMessage.sendErrorMessage(exchange, message);
                    return;
                }

                // The default generations will be 4 if nothing is specified
                int generations = 4;
                // The first item (0) will be an empty String, the second (1) will be "fill"
                // so I skip to the 3rd (2) & 4th (3)
                // In the case they specify a number of generations:
                if (fillInstructions.length == 4) {
                    try {
                        if (Integer.parseInt(fillInstructions[3]) < 0) {
                            String message = "Generations must be 0 or greater.";
                            HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                            handlerErrorMessage.sendErrorMessage(exchange, message);
                            return;
                        }
                        else {
                            generations = Integer.parseInt(fillInstructions[3]);
                        }
                    } catch (NumberFormatException e) {
                        String message = "Generations must be a NUMBER zero or greater.";
                        HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                        handlerErrorMessage.sendErrorMessage(exchange, message);
                        return;
                    }
                }

                String userName = fillInstructions[2];
                FillRequest fillRequest = new FillRequest(userName, generations);
                FillService fillService = new FillService();

                User nullUser = null;
                Database database = new Database();

                FillResult fillResult = fillService.fill(fillRequest, nullUser, database);

                //rollback transaction if necessary
                if (!fillService.isSuccess()) {
                    database.setAllTransactionsSucceeded(false);
                }
                database.endTransaction();

                respData = gson.toJson(fillResult);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                ReadAndWriteString readAndWriteString = new ReadAndWriteString();
                readAndWriteString.writeString(respData, respBody);
                respBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
