package com.groupthree.mancala.models;

import java.time.LocalDate;

public class User {
    private final Profile profile;

    public User(String username, String firstname, String lastname, String profileImage, LocalDate localDate) {
        this.profile = new Profile(username, firstname, lastname, profileImage, localDate);

    }

    public Profile getProfile() {
        return this.profile;
    }

    public PublicInfo getPublicProfile() {
        return this.profile.getPublicProfile();
    }

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
