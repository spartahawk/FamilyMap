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

import doughawkes.fmserver.dataAccess.Database;
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
                    // (this is not realistic, because clients will use different
                    // auth tokens over time, not the same one all the time).
                    // TODO: PROBABLY PUT THIS IN SERVICE: VVV
                    Database database = new Database();
                    boolean authTokenValid = database.getAuthTokenDao().lookup(authTokenString);
                    if (authTokenValid) {
                        System.out.println("Authtoken and its timestamp valid");
                    }
                    else {
                        System.out.println("Authtoken invalid or its timestamp is expired.");
                    }

                    database.endTransaction();
                    //TODO: PROBABLY PUT THIS IN SERVICE ^^^

                    String theURI = exchange.getRequestURI().toString();
                    String[] personInstructions = theURI.split("/");

                    //length 2 for just Person, length 3 (index 2) for the PersonID
                    Gson gson = new Gson();
                    String respData = "";

                    String personID = "";
                    if (personInstructions.length == 3) {
                        personID = personInstructions[2];
                    }


                    PersonService personService = new PersonService();
                    PersonResult personResult = new PersonResult();

                    if (!personService.isSuccess()) {
                        String message = "Person retreival failed.";
                        // TODO the load values could be wrong (missing, invalid) also
                        sendErrorMessage(exchange, message);
                        return;
                    } else {
                        respData = gson.toJson(personResult);
                    }

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
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

    private void sendErrorMessage(HttpExchange exchange, String message) {
        Gson gson = new Gson();
        ErrorMessage errorMessage = new ErrorMessage(message);
        String respData = gson.toJson(errorMessage);
        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
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
