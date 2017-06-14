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
import hawkes.model.request.LoginRequest;
import hawkes.model.result.ErrorMessage;
import hawkes.model.result.LoginResult;


public class LoginHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		boolean success = false;

		try {
			if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                Gson gson = new Gson();
                LoginRequest loginRequest = gson.fromJson(readString(reqBody), LoginRequest.class);
                reqBody.close();

                LoginService loginService = new LoginService();
                LoginResult loginResult = loginService.login(loginRequest);

				String respData = "";
				if (loginResultFailed(loginResult)) {
					// TODO: possibly factor this out
					String message = "Login failed either due to invalid request or no database match.";
					ErrorMessage errorMessage = new ErrorMessage(message);
					respData = gson.toJson(errorMessage);
				}
				else {
					respData = gson.toJson(loginResult);
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
		}
		catch (IOException e) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
			exchange.getResponseBody().close();
			e.printStackTrace();
		}
	}

	private boolean loginResultFailed(LoginResult loginResult) {
		if (loginResult.getAuthToken().equals("") || loginResult.getPersonId().equals("")) {
			return true;
		}
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
