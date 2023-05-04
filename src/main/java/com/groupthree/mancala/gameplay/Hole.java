package com.groupthree.mancala.gameplay;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a hole on the mancala board, which can contain a list of stones.
 *
 * @author Yebo
 * @version 1.0
 */
public class Hole {

    private List<Stone> stones;

    /**
     * Constructs a new empty hole object.
     */
    public Hole() {
        this.stones = new ArrayList<>();
    }

    /**
     * Constructs a new hole object with the given stones.
     *
     * @param stones the stones to initially place in the hole
     */
    public Hole(Stone... stones) {
        this.stones = new ArrayList<>();
        this.stones.addAll(List.of(stones));
    }

    /**
     * Adds stones to the hole.
     *
     * @param stones the stones to add to the hole
     */
    public void addStones(Stone... stones) {
        this.stones.addAll(List.of(stones));
    }

    /**
     * Returns the number of stones in the hole.
     *
     * @return the number of stones in the hole
     */
    public int checkStoneCount() {
        return stones.size();
    }

    /**
     * Removes all stones from the hole and returns them in a list.
     *
     * @return a list containing all stones that were in the hole
     */
    public List<Stone> removeStones() {
        var temp = stones;
        stones = new ArrayList<>();
        return temp;
    }

    /**
     * Returns whether the hole is empty (contains no stones).
     *
     * @return true if the hole is empty, false otherwise
     */
    public boolean isEmpty() {
        return stones.isEmpty();
    }

    /**
     * Removes a single stone from the hole and returns it.
     *
     * @return the stone that was removed from the hole
     * @throws IndexOutOfBoundsException if the hole is empty
     */
    public Stone removeSingleStone() {
        return stones.remove(0);
    }

    /**
     * Returns a string representation of this hole object.
     *
     * @return a string representing the number of stones in the hole
     */
    @Override
    public String toString() {
        return "Hole{" + stones.size() + "}";
    }
}
