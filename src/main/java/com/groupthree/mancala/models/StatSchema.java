package com.groupthree.mancala.models;

import com.groupthree.mancala.gameplay.PowerUp;
import com.groupthree.mancala.gameplay.SpecialStone;

import java.util.Map;

public class StatSchema {
    private Map<PowerUp, Integer> powerUpStats;
    private Map<SpecialStone, Integer> specialStoneStats;


    public StatSchema(Map<PowerUp, Integer> powerUpStats, Map<SpecialStone, Integer> specialStoneStats) {
        this.powerUpStats = powerUpStats;
        this.specialStoneStats = specialStoneStats;
    }

    public StatSchema() {
    }

    public Map<PowerUp, Integer> getPowerUpStats() {
        return powerUpStats;
    }
    public Map<SpecialStone, Integer> getSpecialStoneStats(){return specialStoneStats;}

    public void setPowerUpStats(Map<PowerUp, Integer> powerUpStats) {
        this.powerUpStats = powerUpStats;
    }
}
