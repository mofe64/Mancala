package com.groupthree.mancala.models;

import com.groupthree.mancala.gameplay.PowerUp;
import com.groupthree.mancala.gameplay.SpecialStone;

import java.util.Map;

/**
 * The StatSchema class represents a statistical schema for storing and retrieving game statistics.
 *
 * @author mofe
 * @version 1.0
 */
public class StatSchema {
    private Map<PowerUp, Integer> powerUpStats;
    private Map<SpecialStone, Integer> specialStoneStats;


    /**
     * Creates a new StatSchema object with the given maps of power-up and special stone usage statistics.
     *
     * @param powerUpStats      the map of power-up usage statistics
     * @param specialStoneStats the map of special stone usage statistics
     */
    public StatSchema(Map<PowerUp, Integer> powerUpStats, Map<SpecialStone, Integer> specialStoneStats) {
        this.powerUpStats = powerUpStats;
        this.specialStoneStats = specialStoneStats;
    }

    /**
     * Creates a new empty StatSchema object.
     */
    public StatSchema() {
    }

    /**
     * Returns the map of power-up usage statistics.
     *
     * @return the map of power-up usage statistics
     */
    public Map<PowerUp, Integer> getPowerUpStats() {
        return powerUpStats;
    }

    /**
     * Returns the map of special stone usage statistics.
     *
     * @return the map of special stone usage statistics
     */
    public Map<SpecialStone, Integer> getSpecialStoneStats() {
        return specialStoneStats;
    }

    /**
     * Sets the map of power-up usage statistics.
     *
     * @param powerUpStats the new map of power-up usage statistics to set
     */
    public void setPowerUpStats(Map<PowerUp, Integer> powerUpStats) {
        this.powerUpStats = powerUpStats;
    }
}
