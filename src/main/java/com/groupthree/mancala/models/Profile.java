package com.groupthree.mancala.models;

import java.time.LocalDateTime;

public class Profile {

    private String firstname;
    private String lastname;
    private LocalDateTime lastLoggedIn;


    private String username;
    private String profileImage;
    private double winPercentage;


    public Profile(String username, String firstname, String lastname, String profileImage) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profileImage = profileImage;
        this.winPercentage = 0;
        this.lastLoggedIn = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public PublicInfo getPublicProfile(){
        return new PublicInfo(username, profileImage, winPercentage);
    }

    public LocalDateTime getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(LocalDateTime lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

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


