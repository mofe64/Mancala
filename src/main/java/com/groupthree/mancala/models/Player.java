package com.groupthree.mancala.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.groupthree.mancala.gameplay.Board;
import com.groupthree.mancala.models.deserializers.PlayerDeserializer;
import com.groupthree.mancala.models.serializers.PlayerSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = PlayerSerializer.class)
@JsonDeserialize(using = PlayerDeserializer.class)
public class Player extends User {
    private boolean isApproved;
    private List<PublicInfo> favorites;
    private GamePlayRecord record;


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

    public void addToFavorites(PublicInfo profile) {
        favorites.add(profile);
    }

    public void setFavorites(List<PublicInfo> favorites) {
        this.favorites = favorites;
    }

    public void setRecord(GamePlayRecord record) {
        this.record = record;
    }

    public void removeFromFavorites(PublicInfo profile) {
        favorites.remove(profile);
    }

    public List<PublicInfo> getFavorites() {
        return favorites;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public void makeMove(int holeNumber, Board board) {
        board.moveStones(holeNumber);
    }

    public void getLeaderboardPositionUpdate() {

    }

    public void getGamesPlayedSinceLastLogin() {

    }

    public GamePlayRecord getRecord() {
        return record;
    }

    public LocalDate getLastLogin() {
        return getProfile().getLastLoggedIn();
    }

    @Override
    public String toString() {
        return "Player {" +
                "isApproved=" + isApproved +
                ", favorites=" + favorites +
                ", record=" + record +
                "} " + super.toString();
    }
}
