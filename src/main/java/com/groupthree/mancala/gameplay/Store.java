package com.groupthree.mancala.gameplay;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private final List<Stone> stones;

    public Store() {
        this.stones = new ArrayList<>();
    }

    public void addStoneToStore(Stone stone) {
        this.stones.add(stone);
    }

    public void addStoneToStore(List<Stone> newStones) {
        stones.addAll(newStones);
    }

    public int getNumberOfStonesInStore() {
        return stones.size();
    }
}
