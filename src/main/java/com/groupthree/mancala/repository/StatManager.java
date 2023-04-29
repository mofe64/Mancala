package com.groupthree.mancala.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupthree.mancala.exceptions.ApplicationException;
import com.groupthree.mancala.gameplay.PowerUp;
import com.groupthree.mancala.models.Schema;
import com.groupthree.mancala.models.StatSchema;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatManager {
    public static StatManager INSTANCE;

    private final Map<PowerUp, Integer> powerUpStats;

    private StatManager() {
        try {
            File f = new File("stats.json");
            if (f.exists() && !f.isDirectory()) {
                System.out.println("found stats file");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                StatSchema loadedSchema = objectMapper.readValue(new File("stats.json"), StatSchema.class);
                powerUpStats = loadedSchema.getPowerUpStats();
            } else {
                System.out.println("No stats file found");
                powerUpStats = new HashMap<>();
                powerUpStats.put(PowerUp.DOUBLE_POINTS, 0);
                powerUpStats.put(PowerUp.CONTINUE_TURN, 0);
            }
        } catch (Exception e) {
            String message = "Could not initialize stats due to \n" + e.getMessage();
            throw new ApplicationException(message);
        }
    }

    public static StatManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatManager();
        }
        return INSTANCE;
    }

    public void updatePowerUpUseCount(PowerUp powerUp, int count) {
        int oldCount = powerUpStats.get(powerUp);
        powerUpStats.replace(powerUp, oldCount + count);
    }

    public int getPowerUpUseCount(PowerUp powerUp) {
        return powerUpStats.get(powerUp);
    }

    public void writeToFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StatSchema statSchema = new StatSchema(powerUpStats);
            File file = new File("stats.json");
            if (file.exists() && !file.isDirectory()) {
                file.delete();
            }
            objectMapper.writeValue(file, statSchema);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
