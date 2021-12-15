package Base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import static Base.LoadingFiles.individualTweets;

public class TweetingService {
    // create a tweet
    /*public void tweeting(Tweet tweet) {
        String tweetCode = tweet.getIdTweet();
        try (FileOutputStream fileOutputStream = new FileOutputStream(tweetCode)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tweet);
            individualTweets.get(tweet.getSender().getUsername()).add(tweet);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/ // first implementation
    public static void tweeting(Tweet tweet) {
        if (!individualTweets.containsKey(tweet.getSender().getUsername())) {
            individualTweets.put(tweet.getSender().getUsername(), new ArrayList<>());
            individualTweets.get(tweet.getSender().getUsername()).add(tweet);
        }
        else {
            individualTweets.get(tweet.getSender().getUsername()).add(tweet);
        }
    }
    // delete a tweet
    /*public boolean deleting(Tweet deletedTweet) {
        boolean flag = false;
        // assign null to object reference
        if (deletedTweet.getSender().getUsername() != null) {
            Iterator<Tweet> iterator = individualTweets.get(deletedTweet.getSender().getUsername()).iterator();
            while (iterator.hasNext()) {
                Tweet tweetTemp = iterator.next();
                if (tweetTemp.equals(deletedTweet)) {    // to make sure that tweet is really existed
                    String tweetCode = deletedTweet.getIdTweet() + ".txt";
                    File file = new File(tweetCode);
                    if (file.delete()) {
                        individualTweets.get(deletedTweet.getSender().getUsername()).remove(deletedTweet);
                        flag = true;
                    }
                }
            }
            return flag;
        }
        else
            return false;
    }*/ // first implementation
    public static void deleting(Tweet deletedTweet) {
        if (!individualTweets.containsKey(deletedTweet.getSender().getUsername())) {
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
    public static void retweeting(Tweet retweetedTweet, Account retweeter) {
        Retweet retweet = new Retweet("Retweeted from " + retweetedTweet.getSender(),
                retweetedTweet.getSender(),
                retweetedTweet.getText(),
                LocalDateTime.now(),
                0,
                0);
        retweetedTweet.setRetweets(retweetedTweet.getRetweets()+1);
        retweet.setRetweeter(retweeter);
        retweet.setIdTweet(retweetedTweet.getIdTweet() + "-retweeted");
        tweeting(retweetedTweet);
    }

    // add up like number for the tweet
    public static void liking(Tweet likedTweet) {
        likedTweet.setLikes(likedTweet.getLikes()+1);
    }
}
