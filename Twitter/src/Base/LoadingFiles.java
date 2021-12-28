package Base;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class LoadingFiles {
    static LinkedHashSet<String> usernameList = new LinkedHashSet<>();
    static LinkedHashSet<Account> accounts = new LinkedHashSet<>();
    static LinkedHashMap<String, ArrayList<Tweet>> individualTweets = new LinkedHashMap<>();
    static LinkedHashMap<String, ArrayList<String>> followingList = new LinkedHashMap<>();
    public LoadingFiles() {
        usernameList = new LinkedHashSet<>();
        accounts = new LinkedHashSet<>();
        individualTweets = new LinkedHashMap<>();
        followingList = new LinkedHashMap<>();
    }
    /**
     * accounts information
     * load username and password of each account from hard drive and store it in an arraylist to use
     * by listFiles() method , we can get all files that stored in the specific directory
     */
    public static void loadingAccounts() {
        Path path = Paths.get("/database/accounts.txt");
        File file = new File(String.valueOf(path));
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                accounts = (LinkedHashSet<Account>) objectInputStream.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File is not existed");
        }
        for (Account account : accounts) {
            usernameList.add(account.getUsername());
        }
    }
    /**
     *
     * loading tweets from hard drive and store them in a linkedHashmap that the keys are usernames and the values
     * are tweets that every individual texted on platform
     * tip : in line 57 , we must allocate space for arraylist which holds the tweets of every username
     * tip : in line 56 , tweet.getSender() returns an object of Tweet type
     */
    public static void loadingTweets() {
        Path path = Paths.get("/database/tweets.txt");
        File file = new File(String.valueOf(path));
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                individualTweets = (LinkedHashMap<String, ArrayList<Tweet>>) objectInputStream.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File is not existed");
        }
    }
    /**
     * load following list of each account and put it into a hashmap that the keys are account owner and the values are
     * the accounts that owner is following them
     */
    public static void loadingFollowingList() {
        Path path = Paths.get("/database/followingList.txt");
        File file = new File(String.valueOf(path));
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                followingList = (LinkedHashMap<String, ArrayList<String>>) objectInputStream.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File is not existed");
        }
    }

    /*public static void updateFollowingList(Account doerAccount) {
        for (String username : followingList.get(doerAccount.getUsername())) {
            // write new following list of doer account on file ( note that file name is doer's username )
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(doerAccount.getUsername()))) {
                bufferedWriter.write(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/ // this method is not necessary yet (to update all lists of following of every account)



    // write on files from here :
    public static void storingFollowingList() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("/database/followingList.txt")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(followingList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void storingTweets() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("/database/tweets.txt")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(individualTweets);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void storingAccounts() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("/database/accounts.txt")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Account findAccount(String username) {
        Iterator<Account> iterator = accounts.iterator();
        Account account = null;
        while (iterator.hasNext()) {
            account = iterator.next();
            if (account.getUsername().equals(username)) {
                break;
            }
        }
        return account;
    }
    public static Tweet findTweet(String username, String text) {
        Tweet tweet = null;
        Iterator<Tweet> iterator = individualTweets.get(username).iterator();
        while (iterator.hasNext()) {
            tweet = iterator.next();
            if (tweet.getText().equals(text)) {
                break;
            }
        }
        return tweet;
    }
}
