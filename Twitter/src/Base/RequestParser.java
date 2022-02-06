package Base;

import Base.Account;
import Base.Retweet;
import Base.Tweet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static Base.AuthenticationService.signup;
import static Base.LoadingFiles.*;
import static Base.TimelineService.showMyTweets;
import static Base.TweetingService.tweeting;

public abstract class RequestParser {
    private Boolean hasError;
    private int errorCode;
    private static Account currentAccount;
    public RequestParser() {
        currentAccount = null;
        hasError = null;
        errorCode = 0;
    }
    // for parsing and analyzing client's request
    public File requestParse (File file) {
        // loading data :
        new LoadingFiles();
        loadingAccounts();
        loadingTweets();
        loadingFollowingList();
        // start to analyze request :
        Gson gson = new Gson();
        // response file that server will send to client :
        File response = null;
        // boolean variable to see whether method will be executed successfully or not :
        boolean operationOfMethods = false;
        try (FileReader reader = new FileReader(file)){
            JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
            String method = jsonObj.get("method").getAsString();    // getting "method" value
            JsonArray parameterValue = (JsonArray) jsonObj.get("value");
            Iterator<JsonElement> jsonElementIterator = parameterValue.iterator();
            JsonElement jsonElement = jsonElementIterator.next();
            boolean flag = true;
            String username;
            String password;
            Account account;
            Tweet tweet;
            String text;
            ArrayList<Tweet> showedTweets;
            ArrayList<Tweet> timeLine;
            JsonObject responseJsonObject = new JsonObject();
            while (flag) {
                switch (method) {
                    case "signup":  // error code = 1
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        password = jsonElement.getAsJsonObject().get("password").getAsString();
                        String firstName = jsonElement.getAsJsonObject().get("firstName").getAsString();
                        String lastName = jsonElement.getAsJsonObject().get("lastName").getAsString();
                        String bio = jsonElement.getAsJsonObject().get("bio").getAsString();
                        LocalDate birthDate = LocalDate.parse(jsonElement.getAsJsonObject().get("birthDate").getAsString());
                        LocalDate registrationDate = LocalDate.parse(jsonElement.getAsJsonObject().get("registrationDate").getAsString());
                        operationOfMethods = signup(username, password, bio, firstName, lastName, birthDate, registrationDate);
                        if (operationOfMethods) {
                            currentAccount = findAccount(username);
                            hasError = false;
                        } else {
                            System.err.println("signup failed, if you already have signed up please login");
                            hasError = true;
                            errorCode = 1;
                        }
                        responseJsonObject.addProperty("hasError", hasError);
                        responseJsonObject.addProperty("errorCode", hasError);
                        JsonArray responseJsonArray = new JsonArray();
                        responseJsonArray.add(new JsonObject());
                        responseJsonObject.add("result", responseJsonArray);
                        //gson.toJson(responseJsonObject);
                        break;
                    case "login": // error code = 2
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        password = jsonElement.getAsJsonObject().get("password").getAsString();
                        operationOfMethods = AuthenticationService.login(username, password);
                        if (operationOfMethods) {
                            currentAccount = findAccount(username);
                            hasError = false;
                        } else {
                            System.err.println("login failed");
                            hasError = true;
                            errorCode = 2;
                        }
                        break;
                    case "sendTweet": // error code = 3 wrong self username | error code = 4 no information found
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        text = jsonElement.getAsJsonObject().get("text").getAsString();
                        account = findAccount(username);
                        if (!account.equals(null) && currentAccount.equals(account)) {
                            TweetingService.tweeting(new Tweet(account, text));
                            hasError = false;
                        } else if (!currentAccount.equals(account)) {
                            System.err.println("you can not tweet for others");
                            hasError = true;
                            errorCode = 3;
                        }
                        else {
                            System.err.println("no account found for this information");
                            hasError = true;
                            errorCode = 4;
                        }
                        break;
                    case "deleteTweet": // error code = 3 wrong self username | error code = 4 no information found
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        text = jsonElement.getAsJsonObject().get("text").getAsString();
                        tweet = findTweet(username, text);
                        account = findAccount(username);
                        if (!tweet.equals(null) && currentAccount.equals(account)) {
                            TweetingService.deleting(new Tweet(account, text));
                            hasError = false;
                        } else if (!currentAccount.equals(account)) {
                            System.err.println("you can not delete tweets for others");
                            hasError = true;
                            errorCode = 3;
                        }
                        else {
                            System.err.println("the tweet is not found");
                            hasError = true;
                            errorCode = 4;
                        }
                        break;
                    case "reply": // error code = 3 wrong self username | error code = 4 no information found
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        text = jsonElement.getAsJsonObject().get("text").getAsString();
                        String reply = jsonElement.getAsJsonObject().get("reply").getAsString();
                        tweet = findTweet(username, text);
                        account = findAccount(username);
                        if (!tweet.equals(null) && currentAccount.equals(account)) {
                            //TweetingService.reply(new Tweet(account, text),reply);
                            hasError = false;
                        } else if (!currentAccount.equals(account)){
                            System.err.println("you can not reply in role of others");
                            hasError = true;
                            errorCode = 3;
                        }
                        else {
                            System.err.println("the tweet is not found");
                            hasError = true;
                            errorCode = 4;
                        }
                        break;
                    case "showTweetsOf":
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        account = findAccount(username);
                        if (currentAccount.equals(account)) {
                            try {
                                showedTweets = TimelineService.showMyTweets(account);
                                hasError = false;
                            } catch (Exception e) {
                                e.printStackTrace();
                                errorCode = 7;
                            }
                        } else {
                            try {
                                showedTweets = TimelineService.showTweetsOf(account);
                                hasError = false;
                            } catch (Exception e) {
                                e.printStackTrace();
                                errorCode = 8;
                            }
                        }
                        break;
                    case "timeline":
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        account = findAccount(username);
                        try {
                            timeLine = TimelineService.showMyTimeLine(account);
                            hasError = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "follow" : // error code = 5 followed successful | error code = 6 followed unsuccessful
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        String usernameFollowed = jsonElement.getAsJsonObject().get("usernameFollowed").getAsString();
                        account = findAccount(username);          // doer account
                        Account followTarget = findAccount(usernameFollowed);      // target account
                        if (currentAccount.equals(account) && followTarget != null) {
                            // followTarget != null : check if target account existed
                            operationOfMethods = ObserverService.follow(account, followTarget);
                            if (operationOfMethods) {
                                System.out.println("followed successfully");
                                hasError = false;
                                errorCode = 5;
                            }
                            else {
                                System.out.println("followed unsuccessfully");
                                hasError = true;
                                errorCode = 6;
                            }
                        } else if (followTarget == null) {
                            System.err.println("we couldn't find any account for this username, no account is followed");
                            hasError = true;
                            errorCode = 4;
                        }
                        else {
                            System.err.println("you can not access to other's account");
                            hasError = true;
                            errorCode = 3;
                        }
                        break;
                    case "unfollow": // error code = 5 unfollowed successful | error code = 6 unfollowed unsuccessful
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        String usernameUnfollowed = jsonElement.getAsJsonObject().get("usernameUnfollowed").getAsString();
                        account = findAccount(username);          // doer account
                        Account unfollowTarget = findAccount(usernameUnfollowed);      // target account
                        if (currentAccount.equals(account) && unfollowTarget != null) {
                            // target != null : check if target account existed
                            operationOfMethods = ObserverService.follow(account, unfollowTarget);
                            if (operationOfMethods) {
                                System.out.println("unfollowed successfully");
                                hasError = false;
                                errorCode = 5;
                            }
                            else {
                                System.out.println("unfollowed unsuccessfully");
                                hasError = true;
                                errorCode = 6;
                            }
                        } else if (unfollowTarget == null) {
                            System.err.println("we couldn't find any account for this username, no account is followed");
                            hasError = true;
                            errorCode = 4;
                        }
                        else {
                            System.err.println("you can not access to other's account");
                            hasError = true;
                            errorCode = 3;
                        }
                    default:
                        throw new IllegalStateException("Unexpected value: " + method);
                }
                storingAccounts();
                storingFollowingList();
                storingTweets();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.length() != 0)
            return response;
        else {
            System.err.println("Response is empty");
            return null;
            }
    }
    private JsonObject jsonObjectSetter(Boolean hasError, int errorCode) {}
}
