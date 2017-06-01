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

                Gson gson = new Gson();
                String respData = "";
                if (fillInstructions.length < 3) {
                    String message = "Username required in a fill request: i.e. /fill/Bob or /fill/bob/3";
                    ErrorMessage errorMessage = new ErrorMessage(message);
                    respData = gson.toJson(errorMessage);
                    // Todo: http response header bad request or not found?
                }
                if (fillInstructions.length > 4) {
                    String message = "Too many fill instructions. Just a username and optionally the "
                            + "number of generations to fill, i.e. /fill/Bob or /fill/Bob/3";
                    ErrorMessage errorMessage = new ErrorMessage(message);
                    respData = gson.toJson(errorMessage);
                    // Todo: http response header bad request or not found?
                }
                // negative generations
                else if (Integer.parseInt(fillInstructions[4]) < 0) {
                    String message = "Generations must be 0 or greater.";
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

                    if (fillResultFailed(fillResult)) {
                        // TODO: possibly factor this out
                        String message = "Fill failed.";
                        ErrorMessage errorMessage = new ErrorMessage(message);
                        respData = gson.toJson(errorMessage);
                    }
                    else {
                        respData = gson.toJson(fillResult);
                    }
                }

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
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

    private boolean fillResultFailed(FillResult fillResult) {
        //todo change this to something helpful
        //if (fillResult.getSomething().equals(somethingelse)) {
        //    return true;
        //}
        return false;
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
