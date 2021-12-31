package Base;

import com.company.Reply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import static Base.LoadingFiles.individualTweets;

/**
 * tweeting class
 * send tweets.txt
 * delete tweets.txt
 * retweet tweets.txt
 * like tweets.txt
 * reply tweets.txt (not completed yet)
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class TweetingService {

    public static void tweeting(Tweet tweet) {
        if (!individualTweets.containsKey(tweet.getSender().getUsername())) {
            individualTweets.put(tweet.getSender().getUsername(), new ArrayList<>());
            individualTweets.get(tweet.getSender().getUsername()).add(tweet);
        }
        else {
            individualTweets.get(tweet.getSender().getUsername()).add(tweet);
        }
    }

    public static void deleting(Tweet deletedTweet) {
        if (individualTweets.containsKey(deletedTweet.getSender().getUsername())) {
            if (individualTweets.get(deletedTweet.getSender().getUsername()).contains(deletedTweet)) {
                individualTweets.get(deletedTweet.getSender().getUsername()).remove(deletedTweet);
            }
            else {
                System.err.println("The Tweet has not been existed");
            }
        }
        else {
            System.err.println("The account has not tweeted anything yet");
        }
    }
    // assign tweet to another account
    public static void retweeting(Tweet retweetedTweet, String usernameOfRetweeter) {
        Retweet retweet = new Retweet("Retweeted from " + retweetedTweet.getSender(),
                retweetedTweet.getSender(),
                retweetedTweet.getText(),
                LocalDateTime.now(),
                0,
                0);
        retweetedTweet.setRetweets(retweetedTweet.getRetweets()+1);
        retweetedTweet.getRetweeters().add(usernameOfRetweeter);
        retweetedTweet.setRetweeters(retweetedTweet.getRetweeters());
        tweeting(retweetedTweet);
    }

    // add up like number for the tweet
    public static void liking(Tweet likedTweet, String usernameOfLiker) {
        likedTweet.setLikes(likedTweet.getLikes()+1);
        likedTweet.getLikers().add(usernameOfLiker);
        likedTweet.setLikeList(likedTweet.getLikers());
    }
    public static void replying(Tweet tweet, Reply reply) {}
}
