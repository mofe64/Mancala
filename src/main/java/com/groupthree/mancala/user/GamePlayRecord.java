package com.groupthree.mancala.user;

public class GamePlayRecord {
    private int numberOfWins;
    private int numberOfLosses;
    private int numberOfGames;

    public GamePlayRecord() {
        this.numberOfWins = 0;
        this.numberOfLosses = 0;
        this.numberOfGames = 0;
    }

    public void incrementWins() {
        numberOfWins++;
    }

    public void incrementLosses() {
        numberOfLosses++;
    }

    public void incrementGames() {
        numberOfGames++;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public int getNumberOfLosses() {
        return numberOfLosses;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    @Override
    public String toString() {
        return "users.GamePlayRecord{" +
                "numberOfWins=" + numberOfWins +
                ", numberOfLosses=" + numberOfLosses +
                ", numberOfGames=" + numberOfGames +
                '}';
    }
}
