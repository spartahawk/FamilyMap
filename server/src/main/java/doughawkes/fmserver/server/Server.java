package doughawkes.fmserver.server;

import java.sql.*;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import doughawkes.fmserver.dataAccess.AuthTokenDao;
import doughawkes.fmserver.server.handlers.ClearHandler;
import doughawkes.fmserver.server.handlers.DefaultHandler;
import doughawkes.fmserver.server.handlers.EventHandler;
import doughawkes.fmserver.server.handlers.FillHandler;
import doughawkes.fmserver.server.handlers.LoadHandler;
import doughawkes.fmserver.server.handlers.LoginHandler;
import doughawkes.fmserver.server.handlers.PersonHandler;
import doughawkes.fmserver.server.handlers.RegisterHandler;


/*
	The Server class is the "main" class for the server (i.e., it contains the 
		"main" method for the server program).
	When the server runs, all command-line arguments are passed in to Server.main.
	For this server, the only command-line argument is the port number on which 
		the server should accept incoming client connections.
*/
public class Server {

	// The maximum number of waiting incoming connections to queue.
	// While this value is necessary, for our purposes it is unimportant.
	// Take CS 460 for a deeper understanding of what it means.
	private static final int MAX_WAITING_CONNECTIONS = 12;

	// Java provides an HttpServer class that can be used to embed
	// an HTTP server in any Java program.
	// Using the HttpServer class, you can easily make a Java
	// program that can receive incoming HTTP requests, and respond 
	// with appropriate HTTP responses.
	// HttpServer is the class that actually implements the HTTP network
	// protocol (be glad you don't have to).
	// The "server" field contains the HttpServer instance for this program,
	// which is initialized in the "run" method below.
	private HttpServer server;

	// This method initializes and runs the server.
	// The "portNumber" parameter specifies the port number on which the 
	// server should accept incoming client connections.
	private void run(String portNumber) {
		
		// Since the server has no "user interface", it should display "log"
		// messages containing information about its internal activities.
		// This allows a system administrator (or you) to know what is happening 
		// inside the server, which can be useful for diagnosing problems
		// that may occur.
		System.out.println("Initializing HTTP Server");
		
		try {
			// Create a new HttpServer object.
			// Rather than calling "new" directly, we instead create
			// the object by calling the HttpServer.create static factory method.
			// Just like "new", this method returns a reference to the new object.
			server = HttpServer.create(
						                new InetSocketAddress(Integer.parseInt(portNumber)),
						                MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Indicate that we are using the default "executor".
		// This line is necessary, but its function is unimportant for our purposes.
		server.setExecutor(null);

		// Log message indicating that the server is creating and installing
		// its HTTP handlers.
		// The HttpServer class listens for incoming HTTP requests.  When one
		// is received, it looks at the URL path inside the HTTP request, and
		// forwards the request to the handler for that URL path.
		System.out.println("Creating contexts");

		server.createContext("/user/login", new LoginHandler());
		server.createContext("/user/register", new RegisterHandler());
		server.createContext("/fill", new FillHandler());
		server.createContext("/clear", new ClearHandler());
		server.createContext("/load", new LoadHandler());
		server.createContext("/person", new PersonHandler());
		server.createContext("/event", new EventHandler());

		// This default handler MUST be last so that other urls get handled
        server.createContext("/", new DefaultHandler());

		// Create and install the HTTP handler for the "/routes/claim" URL path.
		// When the HttpServer receives an HTTP request containing the
		// "/routes/claim" URL path, it will forward the request to ClaimRouteHandler
		// for processing.

		
		// Log message indicating that the HttpServer is about the start accepting
		// incoming client connections.
		System.out.println("Starting server");
		
		// Tells the HttpServer to start accepting incoming client connections.
		// This method call will return immediately, and the "main" method
		// for the program will also complete.
		// Even though the "main" method has completed, the program will continue
		// running because the HttpServer object we created is still running
		// in the background.
		server.start();
		
		// Log message indicating that the server has successfully started.
		System.out.println("Server started");
	}

	// "main" method for the server program
	// "args" should contain one command-line argument, which is the port number
	// on which the server should accept incoming client connections.
	public static void main(String[] args) {


		try {
			// Giving the AuthTokenDao class the timelimit as a static field.
			int timeLimitMinutes = Integer.parseInt(args[1]);
			AuthTokenDao.timeLimitMinutes = timeLimitMinutes;
			System.out.printf("Login sessions will have %d minute(s) for access.\n", timeLimitMinutes);
		} catch (NumberFormatException e) {
			// if the argument isn't provided or is badly formatted set to default.
			System.out.println("No valid time limit provided. Setting to 10 minutes.");
			int defaultTimeLimit = 10;
			AuthTokenDao.timeLimitMinutes = defaultTimeLimit;
		}

		String portNumber = args[0];
		new Server().run(portNumber);

        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            System.out.println("ERROR! Could not load database driver");
            // I had to actually go to Project Structure and add the
            // dependency for the sqlite-jdbc-3.7.2.jar file. It didn't matter that
            // I put it in the libs foldler, there was no automatic detection.
        }
	}
}

