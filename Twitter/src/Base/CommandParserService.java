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

public class CommandParserService {
    static Scanner scanner = new Scanner(System.in);
    private static FileWriter file;
    private String name;
    private static int fileNumber = 0;
    public String CommandParserService() {
        JSONObject jsonObjectTemp = new JSONObject();
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        System.out.println("Enter the method you want to run : \n " +
                "1-sign up \n " +
                "2-login \n " +
                "3-add tweet \n " +
                "4-show tweet of \n " +
                "5-timeline \n " +
                "6-delete tweet \n " +
                "7-like \n " +
                "8-reply \n" +
                "9-follow \n" +
                "10-unfollow \n");
        String method = scanner.nextLine();
        String username;
        String password;
        String text;
        switch (method) {
            case "1":
                objMethod.put("method", "signup");
                System.out.println("login :");
                System.out.println("first name :");
                String firstName = scanner.nextLine();
                jsonObjectTemp.put("firstName", firstName);
                System.out.println("last name :");
                String lastName = scanner.nextLine();
                jsonObjectTemp.put("lastName", lastName);
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("password :");
                password = scanner.nextLine();
                jsonObjectTemp.put("password", password);
                System.out.println("birth date :");
                LocalDate birthDate = LocalDate.parse(scanner.nextLine());
                jsonObjectTemp.put("birthDate", birthDate);
                LocalDate registrationDate = LocalDate.now();
                jsonObjectTemp.put("registrationDate", registrationDate);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "2":
                objMethod.put("method", "login");
                System.out.println("login :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("password :");
                password = scanner.nextLine();
                jsonObjectTemp.put("password", password);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "3":
                objMethod.put("method", "tweet");
                System.out.println("tweet :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                jsonObjectTemp.put("text", text);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "4":
                objMethod.put("method", "showTweetOf");
                System.out.println("show tweet of :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "5":
                objMethod.put("method", "timeLine");
                System.out.println("timeline :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "6":
                objMethod.put("method", "delete");
                System.out.println("delete tweet :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                jsonObjectTemp.put("text", text);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "7":
                objMethod.put("method", "like");
                System.out.println("like tweet :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                jsonObjectTemp.put("text", text);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "8":
                objMethod.put("method", "reply");
                System.out.println("reply :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("text of tweet :");
                text = scanner.nextLine();
                jsonObjectTemp.put("text", text);
                System.out.println("text of reply :");
                String reply = scanner.nextLine();
                jsonObjectTemp.put("reply", reply);
                value.add(jsonObjectTemp);
                objMethod.put("parameterValue", value);
                break;
            case "9":
                objMethod.put("method", "follow");
                System.out.println("follow :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("username that must be followed :");
                String usernameFollowed = scanner.nextLine();
                jsonObjectTemp.put("usernameFollowed", usernameFollowed);
            case "10":
                objMethod.put("method", "unfollow");
                System.out.println("unfollow :");
                System.out.println("username :");
                username = scanner.nextLine();
                jsonObjectTemp.put("username", username);
                System.out.println("username that must be unfollowed :");
                String usernameUnfollowed = scanner.nextLine();
                jsonObjectTemp.put("usernameFollowed", usernameUnfollowed);
            default:
                throw new IllegalStateException("Unexpected value, try again ");
        }
        jsonObjectTemp = null;
        try {
            // constructs a FileWriter given a file name
            name = objMethod.get("method").toString() + "-Request-" + fileNumber++ + ".json";
            file = new FileWriter(name);
            file.write(objMethod.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + objMethod);
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
        return name;
    }
    // unnecessary for now
    public String getName() {
        return name;
    }
}