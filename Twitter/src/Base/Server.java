package Base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * server is handling multithreading
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class Server {
    public Server(int port) {
        try (ServerSocket server = new ServerSocket(port)){
            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());
                Session clientSocket = new Session(client);    // client handler
                new Thread(clientSocket).start();              // start a thread for every client
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000); // start server
    }
}
