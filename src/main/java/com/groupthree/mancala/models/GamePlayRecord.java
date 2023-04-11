package com.groupthree.mancala.models;

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

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public void setNumberOfLosses(int numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
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
