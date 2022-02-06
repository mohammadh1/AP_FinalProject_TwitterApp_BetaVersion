package com.company;

import Base.Account;
import Base.Tweet;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.AcceptPendingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class Client {
    static Scanner scanner = new Scanner(System.in);
    public Client(String address, int port) {
        try {
            System.out.println("done");
            Socket socket = new Socket(address, port);
            System.out.println("done");
            //BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectInputStream in = new ObjectInputStream(inputStream);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            System.out.println("login :");
            String firstName = scanner.nextLine();
            String lastName = scanner.nextLine();
            String username = scanner.nextLine();
            String password = scanner.nextLine();
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());
            LocalDate registrationDate = LocalDate.now();
            Account account = new Account(firstName, lastName, birthDate, registrationDate, username, password);
            out.writeObject(account);
            System.out.println("Enter your command : \n 1-write a tweet \n 2-show all tweets \n 3-like a tweet");
            String userInput = scanner.nextLine();
            while (!userInput.equals("exit=1")) {
                switch (userInput) {
                    case "1":
                        String tweetText = scanner.nextLine();
                        LocalDateTime dateTime = LocalDateTime.now();
                        Tweet tweet = new Tweet(account, tweetText, dateTime);
                        out.writeObject(tweet);
                    case "2":
                        ArrayList<Tweet> tweets = new ArrayList<>();
                        out.writeObject("showAllTweet");
                        out.writeObject(account);
                        tweets = (ArrayList<Tweet>) in.readObject();
                        System.out.println(tweets);
                }

            }
            in.close();
            out.flush();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}
