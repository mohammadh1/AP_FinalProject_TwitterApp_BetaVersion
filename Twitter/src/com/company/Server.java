package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;

public class Server {
    public Server(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());
                Session clientSock = new Session(client);
                new Thread(clientSock).start();
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
    }
}
