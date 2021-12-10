package Base;


import java.io.*;

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
        }
    }
    public void unfollow(Account doer, Account target) {

    }
}
