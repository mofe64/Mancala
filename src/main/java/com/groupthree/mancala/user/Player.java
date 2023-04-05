package com.groupthree.mancala.user;


import com.groupthree.mancala.gameplay.Board;

public class Player extends User {
    private boolean isApproved;

    public Player(String username, String firstname, String lastname, String profileImage) {
        super(username, firstname, lastname, profileImage);
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
