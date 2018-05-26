import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class MyHandler extends Handler implements HttpHandler{

    @WebRoute(path = "/")
    public void handleIndex(HttpExchange t) throws IOException{
        String response = "This is the index page, try: /mokus or /test";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(path = "/mokus")
    public void handleMokus(HttpExchange t) throws IOException{
        String response = "mokus";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(path = "/test")
    public void handleTest(HttpExchange t) throws IOException{
        String response = "This is a basic test";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(path = "/test", method = WebRoute.Method.POST)
    public void handlePostTest(HttpExchange t) throws IOException{
        String response = "This is a POST response";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(path = "/test/<user>")
    public void handleVariableFromTest(HttpExchange t) throws IOException{
        String[] pathArray = t.getRequestURI().getPath().split("/");
        String response = "The variable is: " + pathArray[pathArray.length-1];
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
