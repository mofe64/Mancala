package com.groupthree.mancala.gameplay;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Hole[][] gameRows;
    private final Store player1Store;
    private final Store player2Store;
    private boolean playerOneTurn;
    private boolean gameOver;
    private List<Stone> stonesInHand = new ArrayList<>();

    private int turnPoints = 0;

    public Board() {
        playerOneTurn = true;
        gameRows = new Hole[2][6];
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

    public Hole[][] getGameRows() {
        return gameRows;
    }

    public Store getPlayer1Store() {
        return player1Store;
    }

    public Store getPlayer2Store() {
        return player2Store;
    }

    private void moveAcrossTopRow(int from, Hole[] topRow) {
//        System.out.println("current index " + from);
        // get index of the hole we want to remove stones from
        int currentIndex = from;
        // retrieve the hole from the player row
        Hole hole = topRow[currentIndex];
        // if there are no stones in hand
        if (stonesInHand.isEmpty()) {
            // remove all stones in the current hole and place them in hand
            stonesInHand = hole.removeStones();
            // decrement start index for loop since we have already removed stones from the current hole
            // since we are in the top row, we move counter clock wise, so we decrement
            currentIndex = currentIndex - 1;
        }
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

    private void checkIfGameOver() {

        gameOver = true;
        Hole[] topRow = gameRows[0];
        Hole[] bottomRow = gameRows[1];
        for (Hole hole : topRow) {
            if (!hole.isEmpty()) {
                gameOver = false;
                break;
            }
        }
        if (gameOver) {
            dumpStones(0);
            return;
        }

        for (Hole hole : bottomRow) {
            if (!hole.isEmpty()) {
                gameOver = false;
                break;
            }
        }
        if (gameOver) {
//            System.out.println("Game is over, board check method ...");
            dumpStones(1);
        }
    }

    private void dumpStones(int emptyRow) {
        if (emptyRow == 0) {
            Hole[] rowToDump = gameRows[1];
            for (Hole hole : rowToDump) {
                var stones = hole.removeStones();
                player2Store.addStoneToStore(stones);
            }
        }
        if (emptyRow == 1) {
            Hole[] rowToDump = gameRows[0];
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
            // increment start index for loop since we have already removed stones from the current hole
            // since we are in the top row, we move clock wise, so we increment
            currentIndex = currentIndex + 1;
        }
        // distribute stones across the second row
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

    public void moveStones(int from) {
        checkIfGameOver();
        if (gameOver) {
//            System.out.println("Game is over .... from board ");
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

    public void resetTurnPoints() {
        turnPoints = 0;
    }

    public boolean isPlayerOneTurn() {
        return playerOneTurn;
    }

    public int getTurnPoints() {
        return turnPoints;
    }

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


    public boolean gameOver() {
        checkIfGameOver();
        return gameOver;
    }

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


}
