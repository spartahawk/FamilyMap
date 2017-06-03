package doughawkes.fmserver.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Event;
import doughawkes.fmserver.services.EventService;
import doughawkes.fmserver.services.result.EventResult;

/**
 * Created by yo on 6/2/17.
 */

public class EventHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Event handler");
        boolean success = false;

        //
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    String authTokenString = reqHeaders.getFirst("Authorization");

                    // Verify authtoken validity and timestamp expiration
                    Database database = new Database();
                    String userName = database.getAuthTokenDao().lookup(authTokenString);
                    boolean authTokenValid = database.getAuthTokenDao().isSuccess();
                    if (authTokenValid) {
                        System.out.println("Authtoken and its timestamp valid");
                    }
                    else {
                        String message = "Authtoken invalid or its timestamp is expired.";
                        database.setAllTransactionsSucceeded(false);
                        database.endTransaction();
                        HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                        handlerErrorMessage.sendErrorMessage(exchange, message);
                        return;
                    }
                    database.endTransaction();

                    //Extract the parameters from the URI
                    String theURI = exchange.getRequestURI().toString();
                    String[] eventInstructions = theURI.split("/");

                    //length 2 for just Event, length 3 (index 2) for the EventID
                    Gson gson = new Gson();
                    String respData = "";

                    EventService eventService = new EventService();
                    // I'm going to use an actual event object since an eventResult would be redundant
                    Event event = null;
                    ArrayList<Event> userFamilyEventsResult = null;
                    if (eventInstructions.length == 3) {
                        //just getting the one user event
                        String eventID = eventInstructions[2];
                        event = eventService.getEvent(eventID, userName);
                        if (event == null) {
                            String message = "This event does not belong to someone "
                                           + "in the User family.";
                            HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                            handlerErrorMessage.sendErrorMessage(exchange, message);
                            return;
                        }

                    }
                    else if (eventInstructions.length == 2) {
                        // getting the array of the user's family events
                        userFamilyEventsResult = eventService.getUserFamilyEvents(authTokenString);
                    }

                    if (!eventService.isSuccess()) {
                        String message = "Event retreival failed.";
                        HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                        handlerErrorMessage.sendErrorMessage(exchange, message);
                        return;
                    } else {
                        if (eventInstructions.length == 3) {
                            respData = gson.toJson(event);
                        }
                        if (eventInstructions.length == 2) {
                            EventResult eventResult = new EventResult();
                            eventResult.setData(userFamilyEventsResult);
                            respData = gson.toJson(eventResult);
                        }
                    }

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    ReadAndWriteString readAndWriteString = new ReadAndWriteString();
                    readAndWriteString.writeString(respData, respBody);
                    respBody.close();

                    success = true;
                }
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
