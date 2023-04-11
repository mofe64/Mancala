package com.groupthree.mancala.user;


import com.groupthree.mancala.gameplay.Board;

import java.util.ArrayList;
import java.util.List;

public class Player extends User {
    private boolean isApproved;
    private List<User> favorites;
    private GamePlayRecord record;


    public Player(String username, String firstname, String lastname, String profileImage) {
        super(username, firstname, lastname, profileImage);
        this.favorites = new ArrayList<>();
        this.record = new GamePlayRecord();
    }

    public void addToFavorites(User user) {
        favorites.add(user);
    }

    public void removeFromFavorites(User user) {
        favorites.remove(user);
    }

    public List<User> getFavorites() {
        return favorites;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved() {
        isApproved = true;
    }

    public void makeMove(int holeNumber, Board board) {
        board.moveStones(holeNumber);
    }

    public void getLeaderboardPositionUpdate() {

    }

    public void getGamesPlayedSinceLastLogin() {

    }


}
