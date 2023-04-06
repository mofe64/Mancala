package com.groupthree.mancala.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private Profile profile;
    private List<User> favorites;
    private GamePlayRecord record;

    public User(String username, String firstname, String lastname, String profileImage) {
        this.profile = new Profile(username, firstname, lastname, profileImage);
        this.favorites = new ArrayList<>();
        this.record = new GamePlayRecord();
    }

    public void addToFavorites(User user) {
        favorites.add(user);
    }

    public void removeFromFavorites(User user) {
        favorites.remove(user);
    }

    public Map<String, String> getPublicProfile() {
        Map<String, String> publicProfile = new HashMap<>();
        publicProfile.put("username", this.profile.getUsername());
        publicProfile.put("profileImage", this.profile.getProfileImage());
        publicProfile.put("winPercentage", String.valueOf(this.profile.getWinPercentage()));
        return publicProfile;
    }

    public String getUsername() {
        return profile.getUsername();
    }

    public List<User> getFavorites() {
        return favorites;
    }

}
