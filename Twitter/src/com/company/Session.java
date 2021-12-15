package com.company;

import Base.Account;
import Base.Tweet;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static Base.AuthenticationService.signup;
import static Base.TimelineService.showAllTweets;
import static Base.TweetingService.tweeting;

public class Session implements Runnable{
    private Socket socket;

    /**
     * constructor of Session class
     * @param socket socket for every client
     */
    public Session(Socket socket)
    {
        this.socket = socket;
    }
    public void run()
    {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectInputStream in = new ObjectInputStream(inputStream);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            Object object = null;
            StringBuilder total = new StringBuilder();
            while (!object.equals("exit=1")) {
                object = in.readUTF();
                    if (object instanceof Account) {
                        Account account = (Account) object;
                        signup(account.getUsername(), account.getPassword(), account.getFirstName(), account.getLastName(), account.getBirthDate());
                    }
                    else if (object instanceof Tweet) {
                        tweeting((Tweet) object);
                    }
                    else if (object.equals("showAllTweet")) {
                        Account account1 = (Account) in.readObject();
                        out.writeObject(showAllTweets(account1));
                    }
            }
            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }
}
