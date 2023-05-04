package com.groupthree.mancala.gameplay;

import java.util.HashMap;
import java.util.Map;


/**
 * This class represents the game board for the arcade version of the game
 *
 * @author mofe
 * @version 1.0
 **/
public class ArcadeBoard extends Board {
    private final Map<PowerUp, Boolean> playerOnePowerUps;
    private final Map<PowerUp, Boolean> playerTwoPowerUps;


    /**
     * Create an instance of the arcade board
     * initializes all instance variables to their default values
     **/
    public ArcadeBoard() {
        super();
        playerOnePowerUps = new HashMap<>();
        playerOnePowerUps.put(PowerUp.DOUBLE_POINTS, false);
        playerOnePowerUps.put(PowerUp.CONTINUE_TURN, false);
        playerTwoPowerUps = new HashMap<>();
        playerTwoPowerUps.put(PowerUp.DOUBLE_POINTS, false);
        playerTwoPowerUps.put(PowerUp.CONTINUE_TURN, false);
    }

    /**
     * increases the number of stones in the provided players store
     *
     * @param points       : the amount of points to add to the provided player
     * @param playerNumber : the number of the player for which to increase points
     **/

    public void boostPointsInStore(int playerNumber, int points) {
        var store = playerNumber == 1 ? getPlayer1Store() : getPlayer2Store();
        for (int i = 0; i < points; i++) {
            store.addStoneToStore(new Stone());
        }
    }

    /**
     * marks a specific power up as used
     *
     * @param powerUp    : the power up we want to mark as used
     * @param userNumber : the number of the player for which to use the power up
     * @see PowerUp
     **/
    public void usePowerUp(PowerUp powerUp, int userNumber) {
        if (userNumber == 1) {
            playerOnePowerUps.replace(powerUp, false, true);
        } else if (userNumber == 2) {
            playerTwoPowerUps.replace(powerUp, false, true);
        }
    }

    /**
     * checks to see if a specific power up has been used or not
     *
     * @param powerUp    : the power up we want to check for
     * @param userNumber : the number of the player for which to check
     * @return boolean : value indicating if the provided power up has been used or not
     * @see PowerUp
     **/
    public boolean getPowerUpStatus(PowerUp powerUp, int userNumber) {
        if (userNumber == 1) {
            return playerOnePowerUps.get(powerUp);
        } else if (userNumber == 2) {
            return playerTwoPowerUps.get(powerUp);
        }
        return false;
    }
}
