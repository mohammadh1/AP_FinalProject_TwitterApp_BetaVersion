package Base;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import static Base.LoadingFiles.followingList;
public class ObserverService {
    public static boolean follow(Account doer, Account target) {
        if (doer.getFollowing() == 0) {
            followingList.put(doer.getUsername(), new ArrayList<>());
            followingList.get(doer.getUsername()).add(target.getUsername());
            doer.setFollowing(doer.getFollowing() + 1);
            return true;
        }
        else if (doer.getFollowing() != 0) {
            followingList.get(doer.getUsername()).add(target.getUsername());
            doer.setFollowing(doer.getFollowing() + 1);
            return true;
        }
        else
            return false;
    }
    public static boolean unfollow(Account doer, Account target) {
        if (followingList.get(doer).contains(target.getUsername())) {
            followingList.get(doer.getUsername()).remove(target.getUsername());
            doer.setFollowing(doer.getFollowing() - 1);
            return true;
        }
        else
            System.err.println("You have not followed this account yet");
            return false;
    }
}
