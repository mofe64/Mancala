package com.groupthree.mancala.models;

import com.groupthree.mancala.gameplay.PowerUp;

import java.util.List;
import java.util.Map;

public class StatSchema {
    private Map<PowerUp, Integer> powerUpStats;


    public StatSchema(Map<PowerUp, Integer> powerUpStats) {
        this.powerUpStats = powerUpStats;
    }

    public StatSchema() {
    }

    public Map<PowerUp, Integer> getPowerUpStats() {
        return powerUpStats;
    }

    public void setPowerUpStats(Map<PowerUp, Integer> powerUpStats) {
        this.powerUpStats = powerUpStats;
    }
}
