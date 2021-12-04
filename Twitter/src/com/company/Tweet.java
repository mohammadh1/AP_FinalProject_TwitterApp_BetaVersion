package com.company;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tweet {
    private Account sender;
    private String text;
    private LocalDateTime date;
    private int likes;
    private int retweets;

    public Tweet(Account sender, String text, LocalDateTime date, int likes, int retweets) {
        this.sender = sender;
        this.text = text;
        this.date = date;
        this.likes = likes;
        this.retweets = retweets;
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
    /**
     * setter methods of Tweet class for text , likes and retweets number
     */
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
}