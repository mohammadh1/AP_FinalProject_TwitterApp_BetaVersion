package Base;

import java.io.*;
import java.net.Socket;

/**
 * connection service is connected to session , also session is communicating with server and server is handling multithreading
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class ConnectionService {
    /**
     * description of first operation (sending request)
     * 1- Client class gives a json file as a request to ConnectionService class
     * 2- ConnectionService reads json file and sends strings of json request file over dataOutputStream
     * 3- flush dataOutputStream for making sure of everything is written on stream
     * after server does some operation on request : (receiving response)
     * 4- ConnectionService receives some data over dataInputStream
     * 5- ConnectionService writes received data on a json file as response
     * 6- print a "done" string to make sure none of streams are stuck or whatever that stops the process
     *
     * @param address address of socket
     * @param port port of socket
     */
    public ConnectionService (String address, int port,File file) {
        try (Socket socket = new Socket(address, port)){
            File requestFile = new File(String.valueOf(Client.getPath()));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ResponseServer.json"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //---------------------------------
            //first operation
            String str = bufferedReader.readLine();
            dataOutputStream.writeUTF(str);
            //---------------------------------
            dataOutputStream.flush();
            bufferedReader.close();
            //---------------------------------
            // write code
            //---------------------------------
            //second operation
            String str1 = dataInputStream.readUTF();
            bufferedWriter.write(str1);
            //---------------------------------
            ConsoleViewService.terminalStart(new File("ResponseServer.json"));
            System.out.println("done");
            //---------------------------------
            bufferedWriter.close();
            dataOutputStream.close();
            dataInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
