package doughawkes.fmserver.server.handlers;

//import java.io.*;
//import java.net.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Created by yo on 5/23/17.
 */

public class DefaultHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            String theURI = exchange.getRequestURI().toString();
            String filePathStr = "server/data/web" + theURI;
            if (theURI.equals("/")) {
                filePathStr += "index.html";
            }

            Path filePath = FileSystems.getDefault().getPath(filePathStr);
            Files.copy(filePath, exchange.getResponseBody());

/*            File webPageAPIfile = new File("web/index.html");

            FileInputStream fis = new FileInputStream(webPageAPIfile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();


            int nextByte;
            while((nextByte = bis.read()) != -1) {
                baos.write(nextByte);
            }

            OutputStream os = exchange.getResponseBody();
            os.write(baos.toByteArray());

            fis.close();
            bis.close();
            baos.close();
            os.close();*/


            exchange.getResponseBody().close();

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }


    }
}
