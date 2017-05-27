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

import doughawkes.fmserver.services.LoginService;
import doughawkes.fmserver.services.request.LoginRequest;
import doughawkes.fmserver.services.result.LoginResult;


public class LoginHandler implements HttpHandler {

	private int timesVisited = 0;

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		// This handler allows a "Ticket to Ride" player to claim ability
		// route between two cities (part of the Ticket to Ride game).
		// The HTTP request body contains a JSON object indicating which
		// route the caller wants to claim (a route is defined by two cities).
		// This implementation is clearly unrealistic, because it
		// doesn't actually do anything other than print out the received JSON string.
		// It is also unrealistic in that it accepts only one specific
		// hard-coded auth token.
		// However, it does demonstrate the following:
		// 1. How to get the HTTP request type (or, "method")
		// 2. How to access HTTP request headers
		// 3. How to read JSON data from the HTTP request body
		// 4. How to return the desired status code (200, 404, etc.)
		//		in an HTTP response
		// 5. How to check an incoming HTTP request for an auth token

		boolean success = false;

		try {
			// Determine the HTTP request type (GET, POST, etc.).
			// Only allow POST requests for this operation.
			// This operation requires a POST request, because the
			// client is "posting" information to the server for processing.
			if (exchange.getRequestMethod().toLowerCase().equals("post")) {

				// Get the HTTP request headers
                // TO DO: does a login even have headers? Can i remove this? Yes I think so.
				//Headers reqHeaders = exchange.getRequestHeaders();

                // Extract the JSON string from the HTTP request body

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream

                //String reqData = readString(reqBody);
                Gson gson = new Gson();
                LoginRequest loginRequest = gson.fromJson(readString(reqBody), LoginRequest.class);

                reqBody.close();

                LoginService loginService = new LoginService();
                LoginResult loginResult = new LoginResult();
                loginResult = loginService.login(loginRequest);

                // TODO  remove the hardcoded cheats in loginService.login method

                //send response headers
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // translate the java object to JSON
                String respData = gson.toJson(loginResult);

                // Now that the status code and headers have been sent to the client,
                // next we send the JSON data in the HTTP response body.

                // Get the response body output stream.
                OutputStream respBody = exchange.getResponseBody();
                // Write the JSON string to the output stream.
                writeString(respData, respBody);
                // Close the output stream.  This is how Java knows we are done
                // sending data and the response is complete/
                respBody.close();


                // Display/log the request JSON data
                //System.out.println("reqData: " + reqData);

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();
                success = true;

			}

			if (!success) {
				// The HTTP request was invalid somehow, so we return a "bad request"
				// status code to the client.
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				// We are not sending a response body, so close the response body
				// output stream, indicating that the response is complete.
				exchange.getResponseBody().close();
			}
		}
		catch (IOException e) {
			// Some kind of internal error has occurred inside the server (not the
			// client's fault), so we return an "internal server error" status code
			// to the client.
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
			// We are not sending a response body, so close the response body
			// output stream, indicating that the response is complete.
			exchange.getResponseBody().close();

			// Display/log the stack trace
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
