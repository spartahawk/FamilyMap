package doughawkes.fmserver.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import doughawkes.fmserver.dataAccess.Database;
import doughawkes.fmserver.model.Person;
import doughawkes.fmserver.services.PersonService;
import doughawkes.fmserver.services.result.ErrorMessage;
import doughawkes.fmserver.services.result.PersonResult;

/**
 * Created by yo on 6/2/17.
 */

public class PersonHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Person handler");
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {
                    // Extract the auth token from the "Authorization" header
                    String authTokenString = reqHeaders.getFirst("Authorization");

                    // Verify that the auth token is the one we're looking for
                    Database database = new Database();
                    String userName = database.getAuthTokenDao().lookup(authTokenString);
                    boolean authTokenValid = database.getAuthTokenDao().isSuccess();
                    if (authTokenValid) {
                        System.out.println("Authtoken and its timestamp valid");
                    }
                    else {
                        System.out.println("Authtoken invalid or its timestamp is expired.");
                        String message = "Authtoken invalid or its timestamp is expired.";
                        database.setAllTransactionsSucceeded(false);
                        database.endTransaction();
                        HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                        handlerErrorMessage.sendErrorMessage(exchange, message);
                        return;
                    }
                    database.endTransaction();

                    String theURI = exchange.getRequestURI().toString();
                    String[] personInstructions = theURI.split("/");

                    //length 2 for just Person, length 3 (index 2) for the PersonID
                    Gson gson = new Gson();
                    String respData = "";

                    PersonService personService = new PersonService();
                    // I'm going to use an actual person object since a personResult would be redundant
                    Person person = null;
                    ArrayList<Person> userPersonsResult = null;
                    if (personInstructions.length == 3) {
                        //just getting the one user person
                        String personID = personInstructions[2];
                        person = personService.getPerson(personID, userName);
                        if (person == null) {
                            String message = "This person is not in the User family.";
                            HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                            handlerErrorMessage.sendErrorMessage(exchange, message);
                            return;
                        }

                    }
                    else if (personInstructions.length == 2) {
                        // getting the array of the user's people
                        userPersonsResult = personService.getUserPersons(authTokenString);
                    }

                    if (!personService.isSuccess()) {
                        String message = "Person retreival failed.";
                        HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                        handlerErrorMessage.sendErrorMessage(exchange, message);
                        return;
                    } else {
                        if (personInstructions.length == 3) {
                            respData = gson.toJson(person);
                        }
                        if (personInstructions.length == 2) {
                            PersonResult personResult = new PersonResult();
                            personResult.setData(userPersonsResult);
                            respData = gson.toJson(personResult);
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
