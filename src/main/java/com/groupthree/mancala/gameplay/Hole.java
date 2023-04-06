package com.groupthree.mancala.gameplay;

import java.util.ArrayList;
import java.util.List;

public class Hole {

    private List<Stone> stones;

    public Hole() {
        this.stones = new ArrayList<>();
    }

    public Hole(Stone... stones) {
        this.stones = new ArrayList<>();
        this.stones.addAll(List.of(stones));
    }

    public void addStones(Stone... stones) {
        this.stones.addAll(List.of(stones));
    }

    public int checkStoneCount() {
        return stones.size();
    }

    public List<Stone> removeStones() {
        var temp = stones;
        stones = new ArrayList<>();
        return temp;
    }

    public boolean isEmpty() {
        return stones.isEmpty();
    }

    public Stone removeSingleStone() {
        return stones.remove(0);
    }

    @Override
    public String toString() {
        return "Hole{" + stones.size() + "}";
    }
}
