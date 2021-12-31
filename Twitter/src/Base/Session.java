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

/**
 * session is communicating with server and server is handling multithreading
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
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

    /**
     * description of first operation (receiving request)
     * 1- ConnectionService class sends strings of json file as a request to Session class
     * 2- Session reads strings and writes them on a file as request of client
     * 3- and point it for others classes
     * after server does some operation on request : (sending response)
     * 4- Session reads strings of json response file
     * 5- Session writes processed data over dataOutputStream so ConnectionService can get data
     * 6- print a "done" string to make sure none of streams are stuck or whatever that stops the process
     *
     */
    public void run()
    {
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("RequestClient.json"));
            bufferedReader = new BufferedReader(new FileReader("Response.json"));
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //---------------------------------
            //first operation
            String str = dataInputStream.readUTF();
            bufferedWriter.write(str);
            System.out.println(str);
            bufferedWriter.close();
            //---------------------------------
            RequestParser requestParser = new RequestParser();
            File Response = requestParser.requestParse(new File("RequestClient.json"));
            //---------------------------------
            //second operation
            String str1 = bufferedReader.readLine();
            dataOutputStream.writeUTF(str1);
            //---------------------------------
            dataOutputStream.flush();
            System.out.println("done");

            bufferedReader.close();
            dataOutputStream.close();
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
