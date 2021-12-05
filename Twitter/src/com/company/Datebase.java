package com.company;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Datebase {
    public static LinkedHashSet<Account> accounts;
    public static LinkedHashMap<String, ArrayList<Tweet>> individualTweets;

    public Datebase() {
        accounts = new LinkedHashSet<>();
        individualTweets = new LinkedHashMap<>();

    }

}
