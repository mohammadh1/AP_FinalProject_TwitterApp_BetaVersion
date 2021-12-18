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

import static Base.AuthenticationService.signup;
import static Base.TimelineService.showMyTweets;
import static Base.TweetingService.tweeting;

public abstract class CommandParserService {
    // for client's commands (beware all methods are for analyze client's command
    public static Pair<String, JSONArray> commandParser (File file) {
        JSONParser jsonParser = new JSONParser();
        Pair pair = null;
        try {
            Object object = jsonParser.parse(new FileReader(file));
            JSONObject jsonObj = (JSONObject) object;
            String method = (String) jsonObj.get("method");
            JSONArray value = (JSONArray) jsonObj.get("value");
            pair = new Pair(method, value);
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
