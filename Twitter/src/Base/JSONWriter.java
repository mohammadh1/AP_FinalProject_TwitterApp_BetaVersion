package Base;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONWriter {
    static Scanner scanner = new Scanner(System.in);
    private static FileWriter file;
    private String name;
    public JSONWriter() {
        JSONObject obj = new JSONObject();
        JSONArray value = new JSONArray();
        System.out.println("Enter the method you want to run : \n " +
                "1-sign up \n " +
                "2-login \n " +
                "3-add tweet \n " +
                "4-show tweet of \n " +
                "5-timeline \n " +
                "6-delete tweet \n " +
                "7-like \n " +
                "8-reply");
        String method = scanner.nextLine();
        String username;
        String password;
        String text;
        switch (method) {
            case "1":
                obj.put("method", "signup");
                System.out.println("login :");
                System.out.println("first name :");
                String firstName = scanner.nextLine();
                value.add("firstName: " + firstName);
                System.out.println("last name :");
                String lastName = scanner.nextLine();
                value.add("lastName: " + lastName);
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                System.out.println("password :");
                password = scanner.nextLine();
                value.add("password: " + password);
                System.out.println("birth date :");
                LocalDate birthDate = LocalDate.parse(scanner.nextLine());
                value.add("birthDate: " + birthDate);
                LocalDate registrationDate = LocalDate.now();
                value.add("registrationDate: " + registrationDate);
                obj.put("value", value);
                break;
            case "2":
                obj.put("method", "login");
                System.out.println("login :");
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                System.out.println("password :");
                password = scanner.nextLine();
                value.add("password: " + password);
                obj.put("value", value);
                break;
            case "3":
                obj.put("method", "tweet");
                System.out.println("tweet :");
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                value.add("tweet: " + text);
                obj.put("value", value);
                break;
            case "4":
                obj.put("method", "showTweetOf");
                System.out.println("show tweet of :");
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                obj.put("value", value);
                break;
            case "5":
                obj.put("method", "timeLine");
                System.out.println("timeline :");
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                obj.put("value", value);
                break;
            case "6":
                obj.put("method", "delete");
                System.out.println("delete tweet :");
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                value.add("tweet: " + text);
                obj.put("value", value);
                break;
            case "7":
                obj.put("method", "like");
                System.out.println("like tweet :");
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                value.add("tweet: " + text);
                obj.put("value", value);
                break;
            case "8":
                obj.put("method", "reply");
                System.out.println("reply :");
                System.out.println("username :");
                username = scanner.nextLine();
                value.add("username: " + username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                value.add("tweet: " + text);
                System.out.println("text of reply :");
                String reply = scanner.nextLine();
                value.add("reply: " + reply);
                obj.put("value", value);
                break;
        }
        try {
            // constructs a FileWriter given a file name
            name = obj.get(method) + "-Request.txt";
            file = new FileWriter(name);
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getName() {
        return name;
    }
}