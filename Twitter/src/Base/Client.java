package Base;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Client {
    static Scanner scanner = new Scanner(System.in);
    private static Path path;
    public Client() {
        System.out.println("welcome to twitter, you are able to give a file as a request or create a file for request");
        System.out.println("1-requestFile location \n 2-create requestFile \n exit");
        String userInput = scanner.nextLine();
        boolean flag = true;
        while (flag) {
            switch (userInput) {
                case "1":
                    String location = scanner.nextLine().trim();
                    path = Paths.get(location).toAbsolutePath();
                case "2":
                    CommandParserService commandParserService = new CommandParserService();
                    path = Paths.get(commandParserService.getName().trim()).toAbsolutePath();
                case "3":
                    flag = false;
                    break;
            }
        }
    }
    public static Path getPath() {
        return path;
    }
    public static void main(String args[])
    {
        ConnectionService connectionService = new ConnectionService("127.0.0.1", 5000);
    }
}
