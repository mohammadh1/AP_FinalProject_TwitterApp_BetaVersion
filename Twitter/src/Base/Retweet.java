package Base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Retweet extends Tweet implements Serializable {
    private String header;
    private ArrayList<String> retweeters;
    private String idRetweet;
    public Retweet(String header, Account sender, String text, LocalDateTime date, int likes, int retweets) {
        super(sender, text, date, likes, retweets);
        this.header = header;
        idRetweet = (countTweet++) + "";
    }

    public ArrayList<String> getRetweeters() {
        return retweeters;
    }

    public void setRetweeters(ArrayList<String> retweeter) {
        this.retweeters = retweeter;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
