package Base;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Tweet implements Serializable {
    private Account sender;
    private String text;
    private LocalDateTime date;
    private int likes;
    private int retweets;
    public static int countTweet = 0;
    protected String idTweet;

    public Tweet(Account sender, String text, LocalDateTime date, int likes, int retweets) {
        this.sender = sender;
        this.text = text;
        this.date = date;
        this.likes = likes;
        this.retweets = retweets;
        this.idTweet = countTweet++ + "";
    }
    public Tweet(Account sender, String text, LocalDateTime date) {
        this.sender = sender;
        this.text = text;
        this.date = date;
        this.likes = 0;
        this.retweets = 0;
        this.idTweet = countTweet++ + "";
    }

    /**
     * getter methods of Tweet class for all methods
     */
    public Account getSender() {
        return sender;
    }
    public String getText() {
        return text;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public int getLikes() {
        return likes;
    }
    public int getRetweets() {
        return retweets;
    }
    public String getIdTweet() {
        return idTweet;
    }

    /**
     * setter methods of Tweet class for text , likes and retweets number
     */
    protected void setIdTweet(String idTweet) {
        this.idTweet = idTweet;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public boolean checkText() {
        if (this.text.length() > 256) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tweet : " +
                "text='" + text + '\'' +
                ", date=" + date +
                ", likes=" + likes +
                ", retweets=" + retweets +
                '}';
    }
}
