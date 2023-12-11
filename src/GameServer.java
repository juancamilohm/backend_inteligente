import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class GameServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/update-board", new BoardUpdateHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class BoardUpdateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // Aquí deberías procesar la notificación y actualizar el tablero
            // Luego notificar al backend inteligente
            // ...

            String response = "Board updated successfully";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
