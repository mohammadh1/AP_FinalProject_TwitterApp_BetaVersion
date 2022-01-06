package com.twitter.server;

import com.implementation.SessionServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

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
                SessionServiceImpl clientSocket = new SessionServiceImpl(client);    // client handler
                new Thread(clientSocket).start();              // start a thread for every client
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
      int port = 0;
      try (FileReader fileReader = new FileReader("../src/com/resources/server-application.properties")) {
          Properties properties = new Properties();
          properties.load(fileReader);
          port = Integer.parseInt(properties.getProperty("server.port"));
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      Server server = new Server(port); // start server
    }
}
