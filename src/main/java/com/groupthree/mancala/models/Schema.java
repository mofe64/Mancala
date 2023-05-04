package com.groupthree.mancala.models;

import java.util.List;


/**
 * A class representing the schema for a game, which contains lists of players and admins.
 *
 * @author mofe
 * @version 1.0
 */

public class Schema {

    private List<Player> players;
    private List<Admin> admins;

    /**
     * Constructs a new schema object with the given lists of players and admins.
     *
     * @param players the list of players in the game
     * @param admins  the list of admins in the game
     */
    public Schema(List<Player> players, List<Admin> admins) {
        this.players = players;
        this.admins = admins;
    }

    /**
     * Constructs an empty schema object with no players or admins.
     */
    public Schema() {
    }

    /**
     * Returns the list of players in the game.
     *
     * @return the list of players in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players in the game.
     *
     * @param players the new list of players in the game
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Returns the list of admins in the game.
     *
     * @return the list of admins in the game
     */
    public List<Admin> getAdmins() {
        return admins;
    }

    /**
     * Sets the list of admins in the game.
     *
     * @param admins the new list of admins in the game
     */
    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }


    /**
     * Returns a string representation of this schema object.
     *
     * @return a string representation of the object which includes the players list and admin list
     **/
    @Override
    public String toString() {
        return "Schema{" +
                "players=" + players +
                ", admins=" + admins +
                '}';
    }
}
