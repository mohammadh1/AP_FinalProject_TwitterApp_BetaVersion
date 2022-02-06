package Base;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;


public class Client {
    static Scanner scanner = new Scanner(System.in);
    public Client(String address, int port) {
        try {
            Socket socket = new Socket(address, port);
            //BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("login :");
            System.out.println("first name :");
            String firstName = scanner.nextLine();
            System.out.println("last name :");
            String lastName = scanner.nextLine();
            System.out.println("username :");
            String username = scanner.nextLine();
            System.out.println("password :");
            String password = scanner.nextLine();
            System.out.println("birth date :");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());
            LocalDate registrationDate = LocalDate.now();
            Account account = new Account(firstName, lastName, birthDate, registrationDate, username, password);
            out.writeObject(account);
            System.out.println("Enter your command : \n 1-write a tweet \n 2-show all tweets \n 3-like a tweet");
            String userInput = scanner.nextLine();
            while (!userInput.equals("exit")) {
                switch (userInput) {
                    case "1":
                        System.out.print("write your tweet :");
                        String tweetText = scanner.nextLine();
                        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                        Tweet tweet = new Tweet(account, tweetText, dateTime);
                        out.writeObject(tweet);
                        System.out.println("tweeted");
                        break;
                    case "2":
                        out.writeObject("showTweet");
                        ArrayList<Tweet> tweets = (ArrayList<Tweet>) in.readObject();
                        System.out.println(tweets);
                        System.out.println("showed");
                        break;
                }
                System.out.println("Enter your command : \n 1-write a tweet \n 2-show all tweets \n 3-like a tweet");
                userInput = scanner.nextLine();
            }
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
