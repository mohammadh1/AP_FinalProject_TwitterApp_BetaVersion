package Base;

import Base.Account;
import Base.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static Base.LoadingFiles.followingList;
import static Base.LoadingFiles.individualTweets;

public class TimelineService {
    /**
     * showing tweets of a specific account
     * @param account user A that we want to see all tweets which he tweeted
     * @return an array list of tweets that user A tweeted
     */
    public static ArrayList<Tweet> showTweetsOf(Account account) {
        if (individualTweets.get(account).isEmpty()) {
            System.err.println("The account has not tweeted yet");
            return null;
        }
        else
            return individualTweets.get(account);
    }

    /**
     * showing tweets of all accounts that followed by user
     * @param account requester account that wants to see all tweets of accounts that he followed
     * @return an array list of tweets that all followed user tweeted
     */
    public static ArrayList<Tweet> showAllTweets(Account account) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (String username : followingList.get(account.getUsername())) {
            for (Tweet tweet : individualTweets.get(username)) {
                tweets.add(tweet);
            }
        }
        tweets.sort(Comparator.comparing(Tweet::getDate));
        return tweets;
    }

    public static int showLikes(Tweet tweet) {
        return tweet.getLikes();
    }

    public static int showRetweets(Tweet tweet) {
        return tweet.getRetweets();
    }
}
