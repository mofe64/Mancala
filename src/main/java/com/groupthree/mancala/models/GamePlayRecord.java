package com.groupthree.mancala.models;

/**
 * This class represents the gameplay record of a player, including the number of wins, losses, and total games played.
 * An object of this class can be used to keep track of a player's gameplay history.
 * Each record has a number of wins, losses, and total games played. These values can be incremented and retrieved using
 * the provided methods.
 * The record can be initialized with zero values for each category using the default constructor.
 *
 * @author Steve
 * @version 1.0
 */
public class GamePlayRecord {
    private int numberOfWins;
    private int numberOfLosses;
    private int numberOfGames;

    /**
     * Default constructor to initialize the gameplay record with zero values for each category.
     */
    public GamePlayRecord() {
        this.numberOfWins = 0;
        this.numberOfLosses = 0;
        this.numberOfGames = 0;
    }

    /**
     * Increment the number of wins in this record by 1.
     */
    public void incrementWins() {
        numberOfWins++;
    }

    /**
     * Increment the number of losses in this record by 1.
     */
    public void incrementLosses() {
        numberOfLosses++;
    }

    /**
     * Increment the number of games in this record by 1.
     */
    public void incrementGames() {
        numberOfGames++;
    }

    /**
     * Get the current number of wins in this record.
     *
     * @return the number of wins in this record.
     */
    public int getNumberOfWins() {
        return numberOfWins;
    }

    /**
     * Get the current number of losses in this record.
     *
     * @return the number of losses in this record.
     */
    public int getNumberOfLosses() {
        return numberOfLosses;
    }

    /**
     * Get the current number of total games played in this record.
     *
     * @return the number of games in this record.
     */
    public int getNumberOfGames() {
        return numberOfGames;
    }

    /**
     * Set the number of wins in this record.
     *
     * @param numberOfWins the new number of wins.
     */
    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    /**
     * Set the number of losses in this record.
     *
     * @param numberOfLosses the new number of losses.
     */
    public void setNumberOfLosses(int numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    /**
     * Set the number of total games played in this record.
     *
     * @param numberOfGames the new number of games.
     */
    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    /**
     * Returns a string representation of this game play record, including the number of wins, losses, and total games played.
     *
     * @return a string representation of this game play record.
     */
    @Override
    public String toString() {
        return "users.GamePlayRecord{" +
                "numberOfWins=" + numberOfWins +
                ", numberOfLosses=" + numberOfLosses +
                ", numberOfGames=" + numberOfGames +
                '}';
    }
}
