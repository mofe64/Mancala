package com.groupthree.mancala.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private Profile profile;

    public User(String username, String firstname, String lastname, String profileImage) {
        this.profile = new Profile(username, firstname, lastname, profileImage);

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
}
