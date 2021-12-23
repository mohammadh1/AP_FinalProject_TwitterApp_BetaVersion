package com.company;

import Base.Account;
import Base.Retweet;
import Base.Tweet;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static Base.AuthenticationService.signup;
import static Base.TimelineService.showMyTweets;
import static Base.TweetingService.tweeting;

public abstract class CommandParserService {
    // for client's commands (beware all methods are for analyze client's command
    public static Pair<String, LinkedHashMap> commandParser (File file) {
        JSONParser jsonParser = new JSONParser();
        Pair pair = null;
        try {
            Object object = jsonParser.parse(new FileReader(file));
            JSONObject jsonObj = (JSONObject) object;
            String method = (String) jsonObj.get("method");
            JSONArray value = (JSONArray) jsonObj.get("value");
            switch (method) {
                case "login" :
                    Iterator<JSONObject> iterator0 = value.iterator();
                    while (iterator0.hasNext()) {
                        JSONObject element = iterator0.next();
                        String username = (String) element.get("username");
                        String password = (String) element.get("password");
                        String firstname = (String) element.get("firstname");
                        String lastname = (String) element.get("lastname");
                        LocalDate birthdate = (LocalDate) element.get("birthdate");
                        LocalDate bio = (LocalDate) element.get("bio");
                    }
                case "signup" :
                    Iterator<JSONObject> iterator1 = value.iterator();
                    while (iterator1.hasNext()) {
                        JSONObject element = iterator1.next();
                        String username = (String) element.get("username");
                        String password = (String) element.get("password");
                    }
                case "sendTweet" :
                    Iterator<JSONObject> iterator2 = value.iterator();
                    while (iterator2.hasNext()) {
                        JSONObject element = iterator2.next();
                        String username = (String) element.get("username");
                        String tweet = (String) element.get("tweet");
                    }
                case "deleteTweet" :
                    Iterator<JSONObject> iterator3 = value.iterator();
                    while (iterator3.hasNext()) {
                        JSONObject element = iterator3.next();
                        String username = (String) element.get("username");
                        String tweet = (String) element.get("tweet");
                    }
                case "reply" :
                    Iterator<JSONObject> iterator4 = value.iterator();
                    while (iterator4.hasNext()) {
                        JSONObject element = iterator4.next();
                        String username = (String) element.get("username");
                        String tweet = (String) element.get("tweet");
                        String reply = (String) element.get("reply");
                    }
                case "showTweetsOf" :
                    Iterator<JSONObject> iterator5 = value.iterator();
                    while (iterator5.hasNext()) {
                        JSONObject element = iterator5.next();
                        String username = (String) element.get("username");
                    }
                case "timeline" :

            }
            //for (int i =0 ; i < value.size() ; i++)
            //pair = new Pair(method, value);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!pair.equals(null))
            return pair;
        else
            return new Pair<>("Error", null);
    }
}
