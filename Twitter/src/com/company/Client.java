package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public Client(String address, int port) {
        try {
            Socket socket = new Socket(address, port);
            BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while () {

            }
            in.close();
            out.flush();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}
