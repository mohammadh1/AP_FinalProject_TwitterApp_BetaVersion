package Base;


import java.io.*;
import java.util.Iterator;

import static Base.LoadingFiles.followingList;
import static Base.LoadingFiles.updateFollowingList;

public class ObserverService {
    public void follow(Account doer, Account target) {
        if (doer.getFollowing() == 0) {
            String fileName = doer.getUsername();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
                bufferedWriter.write(target.getUsername());
                bufferedWriter.write('\n');
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            followingList.get(doer.getUsername()).add(target.getUsername());
            updateFollowingList(doer);
            doer.getFollowingList().add(target.getUsername());
        }
    }
    public boolean unfollow(Account doer, Account target) {
        boolean flag = false;
        Iterator<String> iterator = doer.getFollowingList().iterator();
        while (iterator.hasNext()) {
            String followedAccount = iterator.next();
            if (followedAccount.equals(target.getUsername())) {
                iterator.remove();
                flag = true;
                break;
            }
        }
        if (flag)
            return true;
        else
            return false;
    }
}
