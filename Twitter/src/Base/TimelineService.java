package Base;

import java.util.ArrayList;
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
    public static ArrayList<Tweet> showAllTweets(Account account) {    // for another account
        return getTweets(account);
    }

    public static ArrayList<Tweet> showMyTweets(Account account) {    // for my account
        ArrayList<Tweet> myTweets = new ArrayList<>();
        for (Tweet tweet : individualTweets.get(account.getUsername())) {
            myTweets.add(tweet);
        }
        myTweets.sort(Comparator.comparing(Tweet::getDate));
        return myTweets;
    }

    private static ArrayList<Tweet> getTweets(Account account) {
        ArrayList<Tweet> timeLine = new ArrayList<>();
        for (String followingUser : followingList.get(account.getUsername())) {
            for (Tweet tweet : individualTweets.get(followingUser)) {
                timeLine.add(tweet);
            }
        }
        timeLine.sort(Comparator.comparing(Tweet::getDate));
        return timeLine;
    }

    public static ArrayList<Tweet> showMyTimeLine(Account account) {
        return getTweets(account);
    }

    public static int showLikes(Tweet tweet) {
        return tweet.getLikes();
    }

    public static int showRetweets(Tweet tweet) {
        return tweet.getRetweets();
    }
}
