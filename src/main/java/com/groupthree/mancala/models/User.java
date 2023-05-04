package com.groupthree.mancala.models;

import java.time.LocalDate;


/**
 * This class represents the base user class for users of the application
 *
 * @author oluwadara
 * @version 1.0
 **/
public abstract class User {
    private final Profile profile;

    /**
     * Constructor for the user class
     *
     * @param username  : The username
     * @param firstname : the firstname of the user
     * @param lastname  : the lastname of the user
     * @param localDate : the creation date of the user
     **/
    public User(String username, String firstname, String lastname, String profileImage, LocalDate localDate) {
        this.profile = new Profile(username, firstname, lastname, profileImage, localDate);

    }

    /**
     * retrieve the full profile for the user
     *
     * @return the user's full profile {@link Profile}
     **/
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * retrieve the public profile for the user
     *
     * @return the user's public profile {@link PublicInfo}
     **/
    public PublicInfo getPublicProfile() {
        return this.profile.getPublicProfile();
    }

    /**
     * retrieve the user's username
     **/
    public String getUsername() {
        return profile.getUsername();
    }

    @Override
    public String toString() {
        return "User{" +
                "profile=" + profile +
                '}';
    }
}
