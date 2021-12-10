package Base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Iterator;

import static Base.LoadingFiles.individualTweets;

public class TweetingService {
    // create a Tweet class
    public void tweeting(Tweet tweet) {
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
    }
    // assign null to object reference11
    public boolean deleting(Tweet deletedTweet) {
        boolean flag = false;
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
    }
    /*
    // assign tweet to another account
    public void retweeting(Tweet retweetedTweet, Account retweeter) {
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
    }*/

    // add up like number for the tweet
    public void liking(Tweet likedTweet) {
        likedTweet.setLikes(likedTweet.getLikes()+1);
    }
}
