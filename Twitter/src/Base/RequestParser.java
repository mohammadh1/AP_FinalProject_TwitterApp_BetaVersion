package Base;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import static Base.AuthenticationService.signup;
import static Base.LoadingFiles.*;

/**
 * mystery method to parse request json in server and do some process on client request
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class RequestParser {
    private Boolean hasError;
    private int errorCode;
    private static Account currentAccount;
    private static int fileNumber = 0;

    /**
     * instantiate RequestParser
     */
    public RequestParser() {
        currentAccount = null;
        hasError = null;
        errorCode = 0;
    }

    /**
     * for parsing the request and call specific methods and do what is written on request
     *
     * @param file copied json request file
     * @return response file
     */
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
        boolean operationOfMethods;
        // starting of parsing
        System.out.println(file.getName());
        try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))){
            // json stuff :
            JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
            String method = jsonObj.get("method").getAsString();    // getting "method" value
            JsonArray parameterValue = (JsonArray) jsonObj.get("parameterValue");
            Iterator<JsonElement> jsonElementIterator = parameterValue.iterator();
            JsonElement jsonElement = jsonElementIterator.next();
            // flag for using to run while loop
            boolean flag = true;
            // variable that use in more than one scope of cases so defines here to avoid DUPLICATE
            String username;
            String password;
            Account account;
            Tweet tweet;
            String text;
            ArrayList<Tweet> showedTweets = null;
            ArrayList<Tweet> timeLine = null;
            // json object that supposed to write on file eventually
            JsonObject responseJsonObject = new JsonObject();
            // an arrayList that saves subObjects that supposed to write in result JsonArray
            ArrayList<JsonObject> listOfResponses = new ArrayList<>();
            ///while (flag) {
                switch (method) {
                    // sign up method
                    case "signup":  // error code = 1
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        password = jsonElement.getAsJsonObject().get("password").getAsString();
                        String firstName = jsonElement.getAsJsonObject().get("firstName").getAsString();
                        String lastName = jsonElement.getAsJsonObject().get("lastName").getAsString();
                        String bio = jsonElement.getAsJsonObject().get("bio").getAsString();
                        LocalDate birthDate = LocalDate.parse(jsonElement.getAsJsonObject().get("birthDate").getAsString());
                        LocalDate registrationDate = LocalDate.parse(jsonElement.getAsJsonObject().get("registrationDate").getAsString());
                        operationOfMethods = signup(username, password, bio, firstName, lastName, birthDate, registrationDate);
                        // sees operation is successful or not
                        System.out.println(operationOfMethods);
                        if (operationOfMethods) {
                            currentAccount = findAccount(username);
                            hasError = false;
                        } else {
                            System.err.println("signup failed, if you already have signed up please login");
                            hasError = true;
                            errorCode = 1;
                        }
                        // creates json object and json file :
                        JsonObject stateOfSignup = new JsonObject();   // state of whether signup is successful or not
                        stateOfSignup.addProperty("state", "done");
                        listOfResponses.add(stateOfSignup);
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    // login method
                    case "login": // error code = 2
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        password = jsonElement.getAsJsonObject().get("password").getAsString();
                        operationOfMethods = AuthenticationService.login(username, password);
                        // sees operation is successful or not
                        if (operationOfMethods) {
                            currentAccount = findAccount(username);
                            hasError = false;
                        } else {
                            System.err.println("login failed");
                            hasError = true;
                            errorCode = 2;
                        }
                        // creates json object and json file :
                        JsonObject stateOfLogin = new JsonObject();   // state of whether login is executed or not
                        stateOfLogin.addProperty("state", "done");
                        listOfResponses.add(stateOfLogin);
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    // tweeting method
                    case "sendTweet": // error code = 3 wrong self username | error code = 4 no information found
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        text = jsonElement.getAsJsonObject().get("text").getAsString();
                        account = findAccount(username);
                        // sees if tweet is for current username
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
                        // creates json object and json file :
                        JsonObject stateOfTweeting = new JsonObject();   // state of whether send operation is executed or not
                        stateOfTweeting.addProperty("state", "done");
                        stateOfTweeting.addProperty("Tweet", text);
                        listOfResponses.add(stateOfTweeting);
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    // delete method
                    case "deleteTweet": // error code = 3 wrong self username | error code = 4 no information found
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        text = jsonElement.getAsJsonObject().get("text").getAsString();
                        tweet = findTweet(username, text);
                        account = findAccount(username);
                        if (!tweet.equals(null) && currentAccount.equals(account)) {
                            TweetingService.deleting(new Tweet(account, text));
                            hasError = false;
                        } else if (!currentAccount.equals(account)) {
                            System.err.println("you can not delete tweets.txt for others");
                            hasError = true;
                            errorCode = 3;
                        }
                        else {
                            System.err.println("the tweet is not found");
                            hasError = true;
                            errorCode = 4;
                        }
                        // creates json object and json file :
                        JsonObject stateOfDeleting = new JsonObject();   // state of whether delete operation is executed or not
                        stateOfDeleting.addProperty("state", "done");
                        stateOfDeleting.addProperty("Tweet", text);
                        listOfResponses.add(stateOfDeleting);
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    //reply method
                    case "reply": // error code = 3 wrong self username | error code = 4 no information found
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        text = jsonElement.getAsJsonObject().get("text").getAsString();
                        String reply = jsonElement.getAsJsonObject().get("reply").getAsString();
                        tweet = findTweet(username, text);
                        account = findAccount(username);
                        if (!tweet.equals(null) && currentAccount.equals(account)) {
                            TweetingService.replying(new Tweet(account, text),new com.company.Reply(findAccount(username), reply, LocalDateTime.now(), 0, 0));
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
                        // creates json object and json file :
                        JsonObject stateOfReplying = new JsonObject();   // state of whether reply operation is executed or not
                        stateOfReplying.addProperty("state", "done");
                        stateOfReplying.addProperty("Tweet", text);
                        stateOfReplying.addProperty("reply",reply);
                        listOfResponses.add(stateOfReplying);
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    // (show tweets.txt of) method
                    case "showTweetsOf":
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        account = findAccount(username);
                        if (currentAccount.equals(account)) {
                            try {
                                showedTweets = TimelineService.showMyTweets(account);
                                hasError = false;
                            } catch (Exception e) {
                                errorCode = 7;
                                hasError = true;
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                showedTweets = TimelineService.showTweetsOf(account);
                                hasError = false;
                            } catch (Exception e) {
                                errorCode = 8;
                                hasError = true;
                                e.printStackTrace();
                            }
                        }
                        // creates json object and json file :
                        for (Tweet twt : showedTweets) {
                            listOfResponses.add((JsonObject) gson.toJsonTree(twt));
                        }
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    // timeline method
                    case "timeline":
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        account = findAccount(username);
                        if (currentAccount.equals(account)) {
                            try {
                                timeLine = TimelineService.showMyTimeLine(account);
                                hasError = false;
                            } catch (Exception e) {
                                errorCode = 7;
                                hasError = true;
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("username is not the that username that logged in");
                            errorCode = 8;
                            hasError = true;
                        }
                        // creates json object and json file :
                        for (Tweet twt : timeLine) {
                            listOfResponses.add((JsonObject) gson.toJsonTree(twt));
                        }
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    // follow method
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
                        // creates json object and json file :
                        JsonObject stateOfFollowing = new JsonObject();   // state of whether follow operation is executed or not
                        stateOfFollowing.addProperty("state", "done");
                        listOfResponses.add(stateOfFollowing);
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    // unfollow method
                    case "unfollow": // error code = 5 unfollowed successful | error code = 6 unfollowed unsuccessful
                        username = jsonElement.getAsJsonObject().get("username").getAsString();
                        String usernameUnfollowed = jsonElement.getAsJsonObject().get("usernameUnfollowed").getAsString();
                        account = findAccount(username);          // doer account
                        Account unfollowTarget = findAccount(usernameUnfollowed);      // target account
                        if (currentAccount.equals(account) && unfollowTarget != null) {
                            // target != null : check if target account existed
                            operationOfMethods = ObserverService.unfollow(account, unfollowTarget);
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
                        // creates json object and json file :
                        JsonObject stateOfUnfollowing = new JsonObject();   // state of whether unfollow operation is executed or not
                        stateOfUnfollowing.addProperty("state", "done");
                        listOfResponses.add(stateOfUnfollowing);
                        errorAndResultSetter(hasError, errorCode, responseJsonObject, listOfResponses);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + method);
                }
                // storing in files with LoadingFiles method
                storingAccounts();
                storingFollowingList();
                storingTweets();
            ///}
            // writes on json file
            //String name = method + "-Response" + fileNumber++ + ".json";
            String name = "Response.json";
            try (JsonWriter writer = new JsonWriter(new FileWriter(name))){
                gson.toJson(responseJsonObject,writer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response = new File("Response.json");
        if (response.length() != 0)
            return response;
        else {
            System.err.println("Response is empty");
            return null;
            }
    }

    /**
     * method for avoiding duplicate codes to write data on json file
     *
     * @param hasError
     * @param errorCode
     * @param responseJsonObject json object that supposed to write on file
     * @param listOfResponses an arrayList that saves subObjects that supposed to write in result JsonArray
     */
    private void errorAndResultSetter(Boolean hasError, int errorCode, JsonObject responseJsonObject, ArrayList<JsonObject> listOfResponses) {
        responseJsonObject.addProperty("hasError", hasError);
        responseJsonObject.addProperty("errorCode", errorCode);
        JsonArray temp = new JsonArray();
        for (JsonObject jsonObject : listOfResponses) {    // json objects are all results object that add to array and array will be add in response object
            temp.add(jsonObject);  // adding all objects of results in array
        }
        responseJsonObject.add("result", temp);   //adding whole array in main object of json
    }
}
