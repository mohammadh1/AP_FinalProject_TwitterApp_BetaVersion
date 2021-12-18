package Base;

import Base.Account;
import Base.Tweet;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static Base.AuthenticationService.signup;
import static Base.TimelineService.showAllTweets;
import static Base.TimelineService.showMyTweets;
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
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            while () {

            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
