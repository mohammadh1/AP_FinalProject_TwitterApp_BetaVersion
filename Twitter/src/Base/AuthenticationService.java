package Base;

import Base.Account;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Iterator;

import static Base.Account.usernameList;
import static Base.EncryptAlgorithm.getSHA;
import static Base.EncryptAlgorithm.toHexString;
import static Base.LoadingFiles.accounts;

public class AuthenticationService {
    public boolean login(String username, String password) {
        boolean flag = false;
        Iterator<Account> it = accounts.iterator();
        while (it.hasNext()) {
            Account account = it.next();
            if (account.getUsername().equals(username)) {
                try {
                    if (account.getPassword().equals(toHexString(getSHA(password)))) {
                        flag = true;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            else {
                flag = false;
            }
        }
        if (flag) {
            System.out.println("Login Successful");
            return true;
        }
        else {
            System.out.println("Login Failed\n if you don't have an account yet , please sign up first");
            return false;
        }
    }
    public void signup(String username, String password, String firstName, String lastName, LocalDate birthDate) {
        for (String str : usernameList) {
            if (str.equals(username)) {
                System.err.println("The username already exists, try again");
            }
            else {
                LocalDate registrationDate = LocalDate.now();
                Account newAccount = new Account(firstName, lastName, birthDate, registrationDate, username, password);
                accounts.add(newAccount);
            }
        }
    }
}
