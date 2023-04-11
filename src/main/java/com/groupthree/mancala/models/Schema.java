package com.groupthree.mancala.models;

import java.util.List;

public class Schema {

    private List<Player> players;
    private List<Admin> admins;

    public Schema(List<Player> players, List<Admin> admins) {
        this.players = players;
        this.admins = admins;
    }
    public Schema(){}

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "players=" + players +
                ", admins=" + admins +
                '}';
    }
}
