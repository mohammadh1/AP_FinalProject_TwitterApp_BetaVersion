package Base;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public abstract class LoadingFiles {
    public static LinkedHashSet<Account> accounts;
    public static LinkedHashMap<String, ArrayList<Tweet>> individualTweets;

    public LoadingFiles() {
        accounts = new LinkedHashSet<>();
        individualTweets = new LinkedHashMap<>();
    }

    public void loading() {
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
    }
}
