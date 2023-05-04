package com.groupthree.mancala.gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This class represents the game board for the traditional version of the game
 *
 * @author mofe
 * @version 1.0
 **/
public class Board {
    protected final Hole[][] gameRows;
    protected final Store player1Store;
    protected final Store player2Store;
    protected boolean playerOneTurn;
    protected boolean gameOver;
    protected List<Stone> stonesInHand = new ArrayList<>();
    private SpecialStone activeStone = SpecialStone.NOT_APPLICABLE;

    protected int turnPoints = 0;

    /**
     * Create an instance of the traditional game board
     * initializes all instance variables to their default values
     **/
    public Board() {
        playerOneTurn = true;
        gameRows = new Hole[2][6];
        // loop through all holes and add 4 stones to each one
        for (Hole[] row : gameRows) {
            for (int i = 0; i < row.length; i++) {
                Stone stone1 = new Stone();
                Stone stone2 = new Stone();
                Stone stone3 = new Stone();
                Stone stone4 = new Stone();
                Hole hole = new Hole(stone1, stone2, stone3, stone4);
                row[i] = hole;
            }
        }
        player1Store = new Store();
        player2Store = new Store();
    }


    /**
     * Retrieves the top and bottom rows of the game board
     *
     * @return gameRows: a 2 dimensional array of {@link Hole} objects representing the top
     * and bottom rows of the game board
     **/
    public Hole[][] getGameRows() {
        return gameRows;
    }

    /**
     * Retrieves the store belonging to player 1
     *
     * @return player1Store: a {@link Store} object belonging to player 1
     **/
    public Store getPlayer1Store() {
        return player1Store;
    }

    /**
     * Retrieves the store belonging to player 2
     *
     * @return player1Store: a {@link Store} object belonging to player 2
     **/
    public Store getPlayer2Store() {
        return player2Store;
    }

    private void moveAcrossTopRow(int from, Hole[] topRow) {
        // get index of the hole we want to remove stones from
        int currentIndex = from;
        // retrieve the hole from the player row
        Hole hole = topRow[currentIndex];
        // if there are no stones in hand (this is the case when this is the first move a player is making)
        if (stonesInHand.isEmpty()) {
            // remove all stones in the current hole and place them in hand
            stonesInHand = hole.removeStones();
            // If we are in arcade mode and a half hand special stone has been selected
            if (activeStone == SpecialStone.HALF_HAND) {
                // divide the stones in hand by 2
                // add the half value back to the hole
                var halfValue = stonesInHand.size() / 2;
                for (int i = 0; i < halfValue; i++) {
                    var stone = stonesInHand.remove(i);
                    hole.addStones(stone);
                }
            }
            // decrement start index for loop since we have already removed stones from the current hole
            currentIndex = currentIndex - 1;
        }
        // if we are in arcade mode and a reverse turn special stone has been selected
        if (activeStone.equals(SpecialStone.REVERSE_TURN)) {
            // instead of moving counter clock wise, we move clock wise cause of the applied reverse turn
            for (int i = currentIndex; i < topRow.length; i++) {
                Hole nextHole = topRow[i];
                if (stonesInHand.size() > 1) {
                    nextHole.addStones(stonesInHand.remove(0));
                } else if (stonesInHand.size() == 1) {
                    if (nextHole.isEmpty()) {
                        nextHole.addStones(stonesInHand.remove(0));
                        playerOneTurn = !playerOneTurn;
                        return;
                    } else {
                        nextHole.addStones(stonesInHand.remove(0));
                        stonesInHand = nextHole.removeStones();
                    }
                }
            }
        } else {
            // since we are in the top row, we move counter clock wise, so we decrement
            // since we are in the first row to move counter clock wise, we need to
            // loop back from the current hole index to 0
            for (int i = currentIndex; i >= 0; i--) {
                // get next hole to remove stone from
                Hole currentHole = topRow[i];
                // if we have more than one stone in hand
                if (stonesInHand.size() > 1) {
                    // remove stone from our hand and add to the current hole
                    currentHole.addStones(stonesInHand.remove(0));
                } else if (stonesInHand.size() == 1) {
                    // if we have only one stone in hand
                    // check to see if the current hole is empty
                    if (currentHole.isEmpty()) {
                        // if empty add the stone in hand to the hole and end turn
                        currentHole.addStones(stonesInHand.remove(0));
                        playerOneTurn = !playerOneTurn;
                        return;
                    } else {
                        // if the current hole is not empty add stone to current hole
                        currentHole.addStones(stonesInHand.remove(0));
                        // remove all stones from current hole and add to hand
                        stonesInHand = currentHole.removeStones();
                    }
                }
            }
        }


    }


    /**
     * make a move on the game board in arcade mode
     *
     * @param from : the hole number we want to start the turn from
     **/
    public void moveStonesArcade(int from) {
        Random random = new Random();
        // simulate a 1 in 10 chance that we encountered a special stone
        boolean encounteredSpecialStone = random.nextFloat() < 0.1;
        // if we did not encounter a special stone we move stones normally via the move stones method
        if (!encounteredSpecialStone) {
            moveStones(from);
        } else {
            // if we did encounter a special stone
            // generate a random number from 1 to 3 to determine which special stone was encountered
            int specialStoneNumber = random.nextInt(3) + 1;
            if (specialStoneNumber == 1) {
                // if generated number is 1 apply reverse turn, then make move
                activeStone = SpecialStone.REVERSE_TURN;
                moveStones(from);
            } else if (specialStoneNumber == 2) {
                // if generated number is 2 apply half hand, then make move
                activeStone = SpecialStone.HALF_HAND;
                moveStones(from);
            }

        }
    }

    private void checkIfGameOver() {
        // assume game over to be true
        gameOver = true;
        Hole[] topRow = gameRows[0];
        Hole[] bottomRow = gameRows[1];
        // loop through all holes in the top row,
        for (Hole hole : topRow) {
            // if current hole is not empty, then game is not over so set game over to false and break
            if (!hole.isEmpty()) {
                gameOver = false;
                break;
            }
        }
        // if after going through the top row, game over is true
        if (gameOver) {
            // dump all remaining stones in player 2's store and return
            dumpStones(0);
            return;
        }

        // loop through all holes in the top row
        for (Hole hole : bottomRow) {
            if (!hole.isEmpty()) {
                // if current hole is not empty, then game is not over so set game over to false and break
                gameOver = false;
                break;
            }
        }
        // if after going through the bottom row, game over is true
        if (gameOver) {
            // dump all remaining stones in player 1's store and return
            dumpStones(1);
        }
    }

    private void dumpStones(int emptyRow) {
        // the top row is empty
        if (emptyRow == 0) {
            Hole[] rowToDump = gameRows[1];
            // remove stones from all holes in bottom row and add them to player 2's store
            for (Hole hole : rowToDump) {
                var stones = hole.removeStones();
                player2Store.addStoneToStore(stones);
            }
        }
        // if bottom row is empty
        if (emptyRow == 1) {
            Hole[] rowToDump = gameRows[0];
            // remove stones from all holes in top row and add them to player 1's store
            for (Hole hole : rowToDump) {
                var stones = hole.removeStones();
                player1Store.addStoneToStore(stones);
            }
        }
    }

    private void moveAcrossBottomRow(int from, Hole[] bottomRow) {
        int currentIndex = from;
        // retrieve the hole from the player row
        Hole hole = bottomRow[currentIndex];
        // if there are no stones in hand
        if (stonesInHand.isEmpty()) {
            // remove all stones in the current hole and place them in hand
            stonesInHand = hole.removeStones();
            // If we are in arcade mode and a half hand special stone has been selected
            if (activeStone.equals(SpecialStone.HALF_HAND)) {
                var halfValue = stonesInHand.size() / 2;
                for (int i = 0; i < halfValue; i++) {
                    var stone = stonesInHand.remove(i);
                    hole.addStones(stone);
                }

            }
            // increment start index for loop since we have already removed stones from the current hole
            // since we are in the top row, we move clock wise, so we increment
            currentIndex = currentIndex + 1;
        }
        if (activeStone.equals(SpecialStone.REVERSE_TURN)) {
            // since we want to move counter clock wise, we need to
            // loop back from the current hole index to 0
            for (int i = currentIndex; i >= 0; i--) {
                // get next hole to remove stone from
                Hole currentHole = bottomRow[i];
                // if we have more than one stone in hand
                if (stonesInHand.size() > 1) {
                    // remove stone from our hand and add to the current hole
                    currentHole.addStones(stonesInHand.remove(0));
                } else if (stonesInHand.size() == 1) {
                    // if we have only one stone in hand
                    // check to see if the current hole is empty
                    if (currentHole.isEmpty()) {
                        // if empty add the stone in hand to the hole and end turn
                        currentHole.addStones(stonesInHand.remove(0));
                        playerOneTurn = !playerOneTurn;
                        return;
                    } else {
                        // if the current hole is not empty add stone to current hole
                        currentHole.addStones(stonesInHand.remove(0));
                        // remove all stones from current hole and add to hand
                        stonesInHand = currentHole.removeStones();
                    }
                }
            }
        } else {
            // distribute stones across the second row in the default clock wise manner
            for (int i = currentIndex; i < bottomRow.length; i++) {
                Hole nextHole = bottomRow[i];
                if (stonesInHand.size() > 1) {
                    nextHole.addStones(stonesInHand.remove(0));
                } else if (stonesInHand.size() == 1) {
                    if (nextHole.isEmpty()) {
                        nextHole.addStones(stonesInHand.remove(0));
                        playerOneTurn = !playerOneTurn;
                        return;
                    } else {
                        nextHole.addStones(stonesInHand.remove(0));
                        stonesInHand = nextHole.removeStones();
                    }
                }
            }
        }
    }

    /**
     * make a move on the game board
     *
     * @param from : the hole number we want to start the turn from
     **/

    public void moveStones(int from) {
        checkIfGameOver();
        if (gameOver) {
            return;
        }
        Hole[] topRow = gameRows[0];
        Hole[] bottomRow = gameRows[1];
        if (from > 0 && from < 7) {
            int startIndex = from - 1;
            if (playerOneTurn) {
                moveAcrossTopRow(startIndex, topRow);
                // if after going through the entire top row, we still have stones in hand
                if (stonesInHand.size() > 0) {
                    // add stone to player 1 store
                    Stone temp = stonesInHand.remove(0);
                    player1Store.addStoneToStore(temp);
                    turnPoints++;
                }
                // if after adding stone to store we still have stones in hand
                if (stonesInHand.size() > 0) {
                    moveAcrossBottomRow(0, bottomRow);
                    // if after distributing across second row we still have stones in hand,
                    // start process again from
                    if (stonesInHand.size() > 0) {
                        moveStones(6);
                    }
                } else {
                    // this means that the player 1 dropped his last stone in his store, so he gets to play again
                    // we return without setting playerOnTurn to false because it's still his turn
                    return;
                }
            } else {
                moveAcrossBottomRow(startIndex, bottomRow);
                // if after going through the entire top row, we still have stones in hand
                if (stonesInHand.size() > 0) {
                    // add stone to player 2 store
                    Stone temp = stonesInHand.remove(0);
                    player2Store.addStoneToStore(temp);
                    turnPoints++;
                }
                // if after adding stone to store we still have stones in hand
                if (stonesInHand.size() > 0) {
                    moveAcrossTopRow(5, topRow);
                    // if after distributing across second row we still have stones in hand,
                    // start process again from
                    if (stonesInHand.size() > 0) {
                        moveStones(1);
                    }
                }
                // this means that the player 2 dropped his last stone in his store, so he gets to play again
                // we return without setting playerOnTurn to false because it's still his turn
            }
        } else {
            throw new IllegalArgumentException("Player One can only choose from 1 to 6");
        }
    }

    /**
     * reset the calculated points accrued after a user turn
     **/
    public void resetTurnPoints() {
        turnPoints = 0;
    }

    /**
     * check to see if it is currently player one's turn or not
     *
     * @return boolean value indicating if it is player one's turn or not
     **/
    public boolean isPlayerOneTurn() {
        return playerOneTurn;
    }

    /**
     * retrieves the calculated points accrued after a user turn
     *
     * @return int value representing the amount of points a user gained in a turn
     **/
    public int getTurnPoints() {
        return turnPoints;
    }

    /**
     * utility method to display a string representation of the game board
     **/
    public void displayBoard() {
        System.out.println("player1Store {" + player1Store.getNumberOfStonesInStore() + "}");
        for (Hole[] row : gameRows) {
            for (Hole hole : row) {
                System.out.print(hole + " ");
            }
            System.out.println();
        }
        System.out.println("player2Store {" + player2Store.getNumberOfStonesInStore() + "}");
    }


    /**
     * Checks to see if the game is over ot not
     *
     * @return a boolean value, indicating if the game is over or bot
     **/
    public boolean gameOver() {
        checkIfGameOver();
        return gameOver;
    }

    /**
     * retrives the player who won the game
     *
     * @return an integer representing the number of the player who won
     * if the game ends in a draw 0 is returned
     **/
    public int checkWinner() {
        var player1Count = player1Store.getNumberOfStonesInStore();
        var player2Count = player2Store.getNumberOfStonesInStore();
        if (player1Count > player2Count) {
            return 1;
        } else if (player1Count == player2Count) {
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * manullay set the player one turn flag to a desired value
     *
     * @param val a boolean representing what we want to change the player one turn flag to
     **/
    public void setPlayerOneTurn(boolean val) {
        this.playerOneTurn = val;
    }

    /**
     * retrieves the special stone that is currently applied
     *
     * @return {@link SpecialStone} the special stone that is currently active
     **/
    public SpecialStone getActiveStone() {
        return activeStone;
    }

    /**
     * resets the active special stone
     **/
    public void resetActiveStone() {
        activeStone = SpecialStone.NOT_APPLICABLE;
    }


}
