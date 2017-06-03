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
import doughawkes.fmserver.services.ClearService;
import doughawkes.fmserver.services.result.ClearResult;
import doughawkes.fmserver.services.result.ErrorMessage;

/**
 * Created by yo on 6/2/17.
 */

public class ClearHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                Gson gson = new Gson();
                String respData = "";

                ClearService clearService = new ClearService();
                ClearResult clearResult = clearService.clear();

                if (!clearService.isSuccess()) {
                    String message = "Clear failed due to an internal error.";
                    sendErrorMessage(exchange, message);
                    return;
                }
                else {
                    respData = gson.toJson(clearResult);
                }

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

    private void sendErrorMessage(HttpExchange exchange, String message) {
        Gson gson = new Gson();
        ErrorMessage errorMessage = new ErrorMessage(message);
        String respData = gson.toJson(errorMessage);
        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            OutputStream respBody = exchange.getResponseBody();
            ReadAndWriteString readAndWriteString = new ReadAndWriteString();
            readAndWriteString.writeString(respData, respBody);
            respBody.close();
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
