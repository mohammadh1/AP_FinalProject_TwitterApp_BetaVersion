package Base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

public class TweetingService {
    // create a Tweet class
    public void tweeting(Tweet tweet) {
        String tweetCode = tweet.getId();
        try (FileOutputStream fileOutputStream = new FileOutputStream(tweetCode)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tweet);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // assign null to object reference11
    public boolean deleting(Tweet deletedTweet) {
        String tweetCode = deletedTweet.getId() + ".txt";
        File file = new File(tweetCode);
            if (file.delete()) {
                return true;
            }
            else
                return false;
    }
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
        retweet.setId(retweetedTweet.getId() + "-retweeted");
        tweeting(retweetedTweet);
    }
    // add up like number for the tweet
    public void liking(Tweet likedTweet) {
        likedTweet.setLikes(likedTweet.getLikes()+1);
    }
}
