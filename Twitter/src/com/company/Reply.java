package com.company;

import Base.Account;
import Base.Tweet;

import java.time.LocalDateTime;

public class Reply extends Tweet {
    public Reply(Account sender, String text, LocalDateTime date, int likes, int retweets) {
        super(sender, text, date, likes, retweets);
    }
}
