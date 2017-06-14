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
import hawkes.model.Person;
import doughawkes.fmserver.services.PersonService;
import hawkes.model.result.ErrorMessage;
import hawkes.model.result.PersonResult;

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
                    // (this is not realistic, because clients will use different
                    // auth tokens over time, not the same one all the time).
                    // TODO: PROBABLY PUT THIS IN SERVICE: VVV
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
                        sendErrorMessage(exchange, message);
                        return;
                    }

                    database.endTransaction();
                    //TODO: PROBABLY PUT THIS IN SERVICE ^^^

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
                            sendErrorMessage(exchange, message);
                            return;
                        }

                    }
                    else if (personInstructions.length == 2) {
                        // getting the array of the user's people
                        userPersonsResult = personService.getUserPersons(authTokenString);
                    }



                    if (!personService.isSuccess()) {
                        String message = "Person retreival failed.";
                        // TODO the load values could be wrong (missing, invalid) also
                        sendErrorMessage(exchange, message);
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
                    writeString(respData, respBody);
                    respBody.close();

                    success = true;
                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }

    private void sendErrorMessage(HttpExchange exchange, String message) {
        Gson gson = new Gson();
        ErrorMessage errorMessage = new ErrorMessage(message);
        String respData = gson.toJson(errorMessage);
        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    The readString method shows how to read a String from an InputStream.
*/
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
    The writeString method shows how to write a String to an OutputStream.
*/
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
