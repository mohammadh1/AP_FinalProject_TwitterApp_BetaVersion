package Base;

import Base.Account;
import Base.Tweet;
import org.json.simple.JSONObject;

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
            FileOutputStream fileOutputStream = new FileOutputStream("Request.txt");
            FileInputStream fileInputStream = new FileInputStream("Response.txt");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int count;
            byte[] bytes = new byte[4096];
            while ((count = dataInputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, count);
            }
            while ((count = fileInputStream.read(bytes)) > 0) {
                dataOutputStream.write(bytes, 0, count);
            }
            fileInputStream.close();
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
