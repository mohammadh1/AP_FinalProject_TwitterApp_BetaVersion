package Base;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public abstract class LoadingFiles {
    public static LinkedHashSet<String> usernameList = new LinkedHashSet<>();
    public static LinkedHashSet<Account> accounts = new LinkedHashSet<>();
    public static LinkedHashMap<String, ArrayList<Tweet>> individualTweets = new LinkedHashMap<>();
    public static LinkedHashMap<String, ArrayList<String>> followingList = new LinkedHashMap<>();
    public LoadingFiles() {

    }
    /**
     * accounts information
     * load username and password of each account from hard drive and store it in an arraylist to use
     * by listFiles() method , we can get all files that stored in the specific directory
     */
    /*public static void loadingAccounts() {
        Path path = Paths.get("/database/information");
        File dir = new File(String.valueOf(path));
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    Account account = (Account) objectInputStream.readObject();
                    accounts.add(account);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Directory is not existed");
        }
    }*/ // first implementation
    // second implementation :
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
    /*public static void loadingTweets() {
        Path path = Paths.get("/database/tweets");
        File dir = new File(String.valueOf(path));
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    Tweet tweet = (Tweet) objectInputStream.readObject();
                    individualTweets.put(tweet.getSender().getUsername(),new ArrayList<>());
                    individualTweets.get(tweet.getSender().getUsername()).add(tweet);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Directory is not existed");
        }
    }*/ // first implementation
    // second implementation :
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
    /*public static void loadingFollowingList() {
        Path path = Paths.get("/database/lists");
        File dir = new File(String.valueOf(path));
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                    String line;
                    // put name of file in key because name of file is the account that followingList is belong to it ( doer account )
                    followingList.put(file.getName(), new ArrayList<>());
                    while ((line = bufferedReader.readLine()) != null) {
                        //adding username strings , these strings are username that the account ( who its name is on file ) is following
                        followingList.get(file.getName()).add(line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Directory is not existed");
        }
    }*/ // first implementation
    // second implementation :
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
}
