package com.groupthree.mancala.gameplay;

import java.util.ArrayList;
import java.util.List;


/**
 * The Store class represents a container for storing stones in a game.
 *
 * @author mofe
 * @version 1.0
 */
public class Store {
    private final List<Stone> stones;

    /**
     * Creates a new Store object with an empty list of stones.
     */
    public Store() {
        this.stones = new ArrayList<>();
    }

    /**
     * Adds a single stone to the store.
     *
     * @param stone the stone to add to the store
     */
    public void addStoneToStore(Stone stone) {
        this.stones.add(stone);
    }

    /**
     * Adds a list of stones to the store.
     *
     * @param newStones the list of stones to add to the store
     */
    public void addStoneToStore(List<Stone> newStones) {
        stones.addAll(newStones);
    }

    /**
     * Returns the number of stones in the store.
     *
     * @return the number of stones in the store
     */
    public int getNumberOfStonesInStore() {
        return stones.size();
    }
}
