package com.groupthree.mancala.models;

import java.time.LocalDate;


/**
 * This class represents the complete profile of a user, including info that should not be public
 *
 * @author mofe
 * @version 1.0
 **/
public class Profile {

    private String firstname;
    private String lastname;
    private LocalDate lastLoggedIn;


    private String username;
    private String profileImage;
    private double winPercentage;


    /**
     * Constructor for the profile
     *
     * @param username     : The username
     * @param firstname    : the firstname of the user
     * @param lastname     : the lastname of the user
     * @param lastLoggedIn : the date the user last logged in
     **/
    public Profile(String username, String firstname, String lastname, String profileImage, LocalDate lastLoggedIn) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profileImage = profileImage;
        this.winPercentage = 0;
        this.lastLoggedIn = lastLoggedIn;
    }

    /**
     * retrieve the user's username
     **/
    public String getUsername() {
        return username;
    }

    /**
     * change the user's username
     *
     * @param username : the new username value
     **/
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * retrieve the user's firstname
     **/
    public String getFirstname() {
        return firstname;
    }

    /**
     * change the user's firstname
     *
     * @param firstname : the new firstname value
     **/
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * retrieve the user's lastname
     **/
    public String getLastname() {
        return lastname;
    }

    /**
     * change the user's lastname
     *
     * @param lastname : the new lastname value
     **/
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * retrieve the user's profile Image
     **/
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * change the user's profile Image
     *
     * @param profileImage : the new profile image value
     **/
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * retrieve the user's public profile
     **/
    public PublicInfo getPublicProfile() {
        return new PublicInfo(username, profileImage, winPercentage);
    }

    /**
     * retrieve the user's last log in date
     **/
    public LocalDate getLastLoggedIn() {
        return lastLoggedIn;
    }

    /**
     * change the user's last login date
     *
     * @param lastLoggedIn : the new lastLoggedIn value
     **/
    public void setLastLoggedIn(LocalDate lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    /**
     * retrieve the user's win percentage
     **/
    public double getWinPercentage() {
        return winPercentage;
    }

    /**
     * change the user's win percentage
     *
     * @param winPercentage : the new winPercentage value
     **/
    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", lastLoggedIn=" + lastLoggedIn +
                ", username='" + username + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", winPercentage=" + winPercentage +
                '}';
    }
}


