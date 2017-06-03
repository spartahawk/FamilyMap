package doughawkes.fmserver.server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import doughawkes.fmserver.services.LoadService;
import doughawkes.fmserver.services.request.LoadRequest;
import doughawkes.fmserver.services.result.LoadResult;

/**
 * Created by yo on 6/2/17.
 */

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("Load handler");
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                Gson gson = new Gson();

                LoadRequest loadRequest = null;
                try {
                    ReadAndWriteString readAndWriteString = new ReadAndWriteString();
                    loadRequest = gson.fromJson(readAndWriteString.readString(reqBody), LoadRequest.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    String message = "Missing or invalid formatting or values.";
                    reqBody.close();
                    HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                    handlerErrorMessage.sendErrorMessage(exchange, message);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                reqBody.close();

                LoadService loadService = new LoadService();
                LoadResult loadResult = loadService.load(loadRequest);

                String respData = "";
                if (!loadService.isSuccess()) {
                    String message = "Load failed.";
                    HandlerErrorMessage handlerErrorMessage = new HandlerErrorMessage();
                    handlerErrorMessage.sendErrorMessage(exchange, message);
                    return;
                }
                else {
                    respData = gson.toJson(loadResult);
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
}