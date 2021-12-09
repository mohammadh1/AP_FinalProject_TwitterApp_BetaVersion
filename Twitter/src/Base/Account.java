package Base;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.LinkedHashSet;

import static Base.EncryptAlgorithm.getSHA;
import static Base.EncryptAlgorithm.toHexString;

/**
 *
 */
public class Account {

    /**
     * fields that must get values in constructor
     */
    private String firstName;
    private String lastName;
    private char[] bio;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private String username;
    private String password;

    /**
     * fields that does not need to get value in constructor and for internal use
     */
    public static LinkedHashSet<String> usernameList;

    /**
     * constructor of Account class
     * instantiate an object of Account class
     * create a new Account
     *
     * @param firstName
     * @param lastName
     * @param bio
     * @param birthDate
     * @param registrationDate
     * @param username
     * @param password
     */
    public Account(String firstName, String lastName, char[] bio, LocalDate birthDate, LocalDate registrationDate, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.username = username;
        usernameList.add(username);
        this.password = password;
    }
    public Account(String firstName, String lastName, LocalDate birthDate, LocalDate registrationDate, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = null;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.username = username;
        usernameList.add(username);
        this.password = password;
        encryptingPassword();
    }

    /**
     * getter methods fot all fields
     */
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public char[] getBio() {
        return bio;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    /**
     * setter methods fot all fields except username , registration date ( once these fields are assigned , no one will be able to change them )
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBio(char[] bio) {
        this.bio = bio;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //check methods start from here : ->

    public boolean checkUsername(String username) {
        for (String strUser : usernameList) {
            try {
                if (strUser.equals(username)) {
                    return false;
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public boolean checkBiography() {
        if (bio.length > 256) {
            return false;
        }
        return true;
    }
    public void encryptingPassword() {
        try
        {
            this.password = toHexString(getSHA(this.password));
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }

}
