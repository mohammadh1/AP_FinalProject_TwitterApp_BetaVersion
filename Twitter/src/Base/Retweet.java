package Base;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Retweet extends Tweet implements Serializable {
    private String header;
    private Account retweeter;
    public Retweet(String header, Account sender, String text, LocalDateTime date, int likes, int retweets) {
        super(sender, text, date, likes, retweets);
        this.header = header;
    }

    public Account getRetweeter() {
        return retweeter;
    }

    public void setRetweeter(Account retweeter) {
        this.retweeter = retweeter;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
