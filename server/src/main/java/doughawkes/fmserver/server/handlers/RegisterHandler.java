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
                if (!registerService.isSuccess()) {
                    String message = "Registration failed. Username may be taken already.";
                    HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                    handlerErrorMessage.sendErrorMessage(exchange, message);
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
}
