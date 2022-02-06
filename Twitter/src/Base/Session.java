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
            Object object = "";
            Account account = null;
            while (!object.equals("exit=1")) {
                object = in.readObject();
                    if (object instanceof Account) {
                        account = (Account) object;
                        signup(account.getUsername(), account.getPassword(), account.getFirstName(), account.getLastName(), account.getBirthDate());
                        System.out.println("account done");
                    }
                    else if (object instanceof Tweet) {
                        tweeting((Tweet) object);
                        System.out.println("tweet done");
                    }
                    else if (object.equals("showTweet")) {
                        out.writeObject(showMyTweets(account));
                        System.out.println("showing tweets done");
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
