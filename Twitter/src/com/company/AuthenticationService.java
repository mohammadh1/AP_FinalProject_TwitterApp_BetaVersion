package com.company;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Iterator;

import static com.company.Account.usernameList;
import static com.company.EncryptAlgorithm.getSHA;
import static com.company.EncryptAlgorithm.toHexString;
import static com.company.Datebase.accounts;

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
                System.err.println("The username already exists");
            }
            else {
                LocalDate registrationDate = LocalDate.now();
                Account newAccount = new Account(firstName, lastName, birthDate, registrationDate, username, password);
                accounts.add(newAccount);
            }
        }
    }
}
