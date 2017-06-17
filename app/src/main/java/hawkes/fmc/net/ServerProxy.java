package hawkes.fmc.net;

/**
 * Created by yo on 6/8/17.
 */

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import hawkes.fmc.model.Model;
import hawkes.model.request.*;
import hawkes.model.result.*;

// I'll want this to be a singleton. So change it.

public class ServerProxy {

    public LoginResult login(String serverHost, String serverPort, LoginRequest loginRequest) {

        // This method shows how to send a POST request to a server
        LoginResult loginResult = new LoginResult();

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("POST");
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);	// There is a request body

            // Add an auth token to the request in the HTTP "Authorization" header
            // http.addRequestProperty("Authorization", "afj232hj2332");

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            Gson gson = new Gson();
            // This is the JSON string we will send in the HTTP request body
            String reqData = gson.toJson(loginRequest);

            // Get the output stream containing the HTTP request body
            OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                Log.v("SERVER PROXY", "HTTP_OK");

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                Log.v("SERVER PROXY", "RESPDATA: " + respData);

                loginResult = gson.fromJson(respData, LoginResult.class);

            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                Log.v("SERVER PROXY", "HTTP_NOT_OK");

                loginResult.setMessage(null);
            }

        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }

        return loginResult;
    }









    public void getPersons(String serverHost, String serverPort) {

        // This method shows how to send a POST request to a server

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("GET");
            // Indicate that this request will contain an HTTP request body
            //TODO: SEE IF THE FOLLOWING LINE IS OKAY OR SHOULD BE OMMITTED
            http.setDoOutput(false);	// There is NO request body

            //get a pointer to the model instance for use here
            Model model = Model.getModel();

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", model.getAuthtoken());

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            Gson gson = new Gson();
            // This is the JSON string we will send in the HTTP request body
            //String reqData = gson.toJson(loginRequest);

            // Get the output stream containing the HTTP request body
            //OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            //writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            //reqBody.close();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                Log.v("SERVER PROXY GETPERSONS", "HTTP_OK");

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                Log.v("SERVER PROXY GETPERSONS", "RESPDATA: " + respData);

                PersonResult personResult = gson.fromJson(respData, PersonResult.class);

                model.setPersons(personResult.getAllFamily());

            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                Log.v("SERVER PROXY GETPERSONS", "HTTP_NOT_OK");

                //loginResult.setMessage(null);
            }

        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }

        //return loginResult;
    }

    public void getEvents(String serverHost, String serverPort) {

        // This method shows how to send a POST request to a server

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("GET");
            // Indicate that this request will contain an HTTP request body
            //TODO: SEE IF THE FOLLOWING LINE IS OKAY OR SHOULD BE OMMITTED
            http.setDoOutput(false);	// There is NO request body

            //get a pointer to the model instance for use here
            Model model = Model.getModel();

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", model.getAuthtoken());

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            Gson gson = new Gson();
            // This is the JSON string we will send in the HTTP request body
            //String reqData = gson.toJson(loginRequest);

            // Get the output stream containing the HTTP request body
            //OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            //writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            //reqBody.close();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                Log.v("SERVER PROXY GETEVENTS", "HTTP_OK");

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                Log.v("SERVER PROXY GETEVENTS", "RESPDATA: " + respData);

                model.setEvents(gson.fromJson(respData, EventResult.class).getData());

            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                Log.v("SERVER PROXY GETEVENTS", "HTTP_NOT_OK");

                //loginResult.setMessage(null);
            }

        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }

        //return loginResult;
    }












    public RegisterResult register(String serverHost, String serverPort, RegisterRequest registerRequest) {

        // This method shows how to send a POST request to a server
        RegisterResult registerResult = new RegisterResult();

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("POST");
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);	// There is a request body

            // Add an auth token to the request in the HTTP "Authorization" header
            // http.addRequestProperty("Authorization", "afj232hj2332");

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            Gson gson = new Gson();
            // This is the JSON string we will send in the HTTP request body
            String reqData = gson.toJson(registerRequest);

            // Get the output stream containing the HTTP request body
            OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                Log.v("SERVER PROXY", "HTTP_OK");

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                Log.v("SERVER PROXY", "Register RESPDATA: " + respData);

                registerResult = gson.fromJson(respData, RegisterResult.class);

            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                Log.v("SERVER PROXY", "HTTP_NOT_OK");

                registerResult.setMessage(null);
            }



        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }

        return registerResult;
    }


//    public RegisterResult register(String serverHost, String serverPort, RegisterRequest registerRequest) {
//
//
//    }


    /*
    The readString method shows how to read a String from an InputStream.
*/
    private static String readString(InputStream is) throws IOException {
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
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
