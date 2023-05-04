package com.groupthree.mancala.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.groupthree.mancala.gameplay.Board;
import com.groupthree.mancala.models.deserializers.PlayerDeserializer;
import com.groupthree.mancala.models.serializers.PlayerSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player which is a subclass of the {@link User} class
 *
 * @author mofe
 * @version 1.0
 **/
@JsonSerialize(using = PlayerSerializer.class)
@JsonDeserialize(using = PlayerDeserializer.class)
public class Player extends User {
    private boolean isApproved;
    private List<PublicInfo> favorites;
    private GamePlayRecord record;


    /**
     * Constructor for the user class
     *
     * @param username  : The username of the player
     * @param firstname : the firstname of the player
     * @param lastname  : the lastname of the player
     * @param localDate : the creation date of the player
     **/
    public Player(
            String username,
            String firstname,
            String lastname,
            String profileImage,
            LocalDate localDate
    ) {
        super(username, firstname, lastname, profileImage, localDate);
        this.favorites = new ArrayList<>();
        this.record = new GamePlayRecord();
    }

    /**
     * Add a player to this player's list of favourites
     *
     * @param profile the public profile of the other player we want to add to favourite
     **/
    public void addToFavorites(PublicInfo profile) {
        favorites.add(profile);
    }

    /**
     * set the player's list of favourites
     *
     * @param favorites a list of public profiles {@link PublicInfo}
     **/
    public void setFavorites(List<PublicInfo> favorites) {
        this.favorites = favorites;
    }

    /**
     * Sets the game play record of the player.
     *
     * @param record The GamePlayRecord to set.
     */
    public void setRecord(GamePlayRecord record) {
        this.record = record;
    }

    /**
     * Removes a player from this player's list of favourites.
     *
     * @param profile The public profile of the other player to remove from favorites.
     */
    public void removeFromFavorites(PublicInfo profile) {
        favorites.remove(profile);
    }

    /**
     * Returns the player's list of favourites.
     *
     * @return A list of public profiles {@link PublicInfo}.
     */
    public List<PublicInfo> getFavorites() {
        return favorites;
    }

    /**
     * Returns whether the player has been approved.
     *
     * @return true if the player is approved, false otherwise.
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Sets whether the player has been approved.
     *
     * @param isApproved A boolean value indicating whether the player is approved.
     */
    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    /**
     * Makes a move in the game.
     *
     * @param holeNumber The hole number to move stones from.
     * @param board      The Board object representing the current game state.
     */
    public void makeMove(int holeNumber, Board board) {
        board.moveStones(holeNumber);
    }

    /**
     * This method retrieves the game play record of the player.
     *
     * @return the game play record of the player {@link GamePlayRecord}
     */
    public GamePlayRecord getRecord() {
        return record;
    }

    /**
     * This method retrieves the last login date of the player.
     *
     * @return the last login date of the player {@link LocalDate}
     */
    public LocalDate getLastLogin() {
        return getProfile().getLastLoggedIn();
    }

    /**
     * Returns a string representation of the player object.
     *
     * @return a string representation of the player object.
     */
    @Override
    public String toString() {
        return "Player {" +
                "isApproved=" + isApproved +
                ", favorites=" + favorites +
                ", record=" + record +
                "} " + super.toString();
    }
}
