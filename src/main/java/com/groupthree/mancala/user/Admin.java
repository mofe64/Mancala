package com.groupthree.mancala.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin extends User {


    public Admin(String username, String firstname, String lastname, String profileImage) {
        super(username, firstname, lastname, profileImage);
    }

    public void approvePlayer(Player player) {
        player.setApproved();
    }

    public List<User> getFrequentUsers() {
        return new ArrayList<>();
    }

    public Map<String, Integer> getPowerUpReport(){
        return new HashMap<>();
    }

    public Map<String, Integer> getSpecialStoneReport(){
        return new HashMap<>();
    }



}
