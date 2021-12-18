package com.company;

import Base.Account;
import Base.Retweet;
import Base.Tweet;

import static Base.AuthenticationService.signup;
import static Base.TimelineService.showMyTweets;
import static Base.TweetingService.tweeting;

public abstract class CommandParserService {
    // for client's commands (beware all methods are for analyze client's command
    public static Object commandParser (Request request) {
        String command = request.getCommand();
        Object value = request.getValue();
        switch (command) {
            case "tweet" :
                if (value instanceof Tweet) {
                    Tweet tweet = (Tweet) value;
                    return tweet;
                }
                else {

                }
            case "retweet" :
                if (value instanceof Retweet) {
                    Retweet retweet = (Retweet) value;
                    return retweet;
                }
                else {

                }
            case "reply" :
                if (value instanceof Reply) {

                }
                else {

                }
            case "delete" :
                if (value instanceof Tweet || value instanceof Reply || value instanceof Retweet) {

                }
                else {

                }
            case "like" :
                if (value instanceof Tweet || value instanceof Reply || value instanceof Retweet) {

                }
                else {

                }
            case "follow" :
                if (value instanceof Account) {

                }
                else {

                }
            case "unfollow" :
                if (value instanceof Account) {

                }
                else {

                }
            case "timeline" :
                if (value instanceof Account) {

                }
                else {

                }
            case "login" :
                if (value instanceof Account) {

                }
                else {

                }
            case "signup" :
                if (value instanceof Account) {

                }
                else {

                }
        }
    }
}
