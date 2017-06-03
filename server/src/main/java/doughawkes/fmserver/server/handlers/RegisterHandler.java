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

import doughawkes.fmserver.services.RegisterService;
import doughawkes.fmserver.services.request.RegisterRequest;
import doughawkes.fmserver.services.result.ErrorMessage;
import doughawkes.fmserver.services.result.RegisterResult;

/**
 * Created by yo on 5/30/17.
 */

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                Gson gson = new Gson();
                ReadAndWriteString readAndWriteString = new ReadAndWriteString();
                RegisterRequest registerRequest = gson.fromJson(readAndWriteString.readString(reqBody),
                                                                RegisterRequest.class);
                reqBody.close();

                RegisterService registerService = new RegisterService();
                RegisterResult registerResult = registerService.register(registerRequest);

                String respData = "";
                if (!registerService.isSuccess() || registerResultFailed(registerResult)) {
                    // TODO: possibly factor this out
                    String message = "Registration failed. Username may be taken already.";
                    sendErrorMessage(exchange, message);
                    return;
                }
                else {
                    respData = gson.toJson(registerResult);
                }

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                readAndWriteString.writeString(respData, respBody);
                respBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private boolean registerResultFailed(RegisterResult registerResult) {
        //todo change this to something helpful
        //if (registerResult.getSomething().equals(somethingelse)) {
        //    return true;
        //}
        return false;
    }

    private void sendErrorMessage(HttpExchange exchange, String message) {
        Gson gson = new Gson();
        ErrorMessage errorMessage = new ErrorMessage(message);
        String respData = gson.toJson(errorMessage);
        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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
