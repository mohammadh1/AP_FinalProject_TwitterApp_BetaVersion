package Base;

import Base.Client;

import java.io.*;
import java.net.Socket;

import static Base.Client.getPath;

public class ConnectionService {
    public ConnectionService (String address, int port) {
        Socket socket = null;
        try {
            socket = new Socket(address, port);
            File requestFile = new File(String.valueOf(getPath()));
            FileOutputStream fileOutputStream = new FileOutputStream("Response.txt");
            FileInputStream fileInputStream = new FileInputStream(requestFile);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            int count;
            byte[] buffer = new byte[4096];
            while ((count = fileInputStream.read(buffer)) > 0) {
                dataOutputStream.write(buffer, 0, count);
            }
            while ((count = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
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
