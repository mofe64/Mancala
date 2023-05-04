package com.groupthree.mancala.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupthree.mancala.exceptions.ApplicationException;
import com.groupthree.mancala.gameplay.PowerUp;
import com.groupthree.mancala.gameplay.SpecialStone;
import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.models.StatSchema;
import com.groupthree.mancala.models.User;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


/**
 * A utility class which acts as a repository for all game stats within the application
 * This class is a singleton and as such there will ever be one instance of the class
 * within the lifetime of the application
 *
 * @author mofe
 * @version 1.0
 **/
public class StatManager {
    public static StatManager INSTANCE;

    private final Map<PowerUp, Integer> powerUpStats;
    private final Map<SpecialStone, Integer> specialStoneStats;

    // private constructor in order to implement the singleton design pattern
    private StatManager() {
        // when an object is created, we first try to restore the values from an existing stats.json file
        // this file serves as file store for all the game stats
        try {
            // create file object
            File f = new File("stats.json");
            // if the stats.json file exists
            if (f.exists() && !f.isDirectory()) {
                System.out.println("found stats file");
                // create an object mapper to deserialize the json file
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                // read json into lour stat schema object
                StatSchema loadedSchema = objectMapper.readValue(new File("stats.json"), StatSchema.class);
                // set the power up and special stone stats
                powerUpStats = loadedSchema.getPowerUpStats();
                specialStoneStats = loadedSchema.getSpecialStoneStats();
            } else {
                // if the stats.json file does not exist
                System.out.println("No stats file found");
                // initialise our instance variables to their default/empty states
                powerUpStats = new HashMap<>();
                powerUpStats.put(PowerUp.DOUBLE_POINTS, 0);
                powerUpStats.put(PowerUp.CONTINUE_TURN, 0);
                specialStoneStats = new HashMap<>();
                specialStoneStats.put(SpecialStone.HALF_HAND, 0);
                specialStoneStats.put(SpecialStone.REVERSE_TURN, 0);
                specialStoneStats.put(SpecialStone.SWITCH_SIDES, 0);

            }
            // handle exception
        } catch (Exception e) {
            String message = "Could not initialize stats due to \n" + e.getMessage();
            throw new ApplicationException(message);
        }
    }

    /***
     Get the instance of our stat manager
     * **/
    public static StatManager getInstance() {
        // if an instance of this object has not been created yet,  then create a new one
        if (INSTANCE == null) {
            INSTANCE = new StatManager();
        }
        return INSTANCE;
    }

    /**
     * Update the use count for a specific power up
     *
     * @param powerUp {@link PowerUp } the power up for which we want to increase the count
     * @param count   the amount by which we want the increase the power up count
     */
    public void updatePowerUpUseCount(PowerUp powerUp, int count) {
        int oldCount = powerUpStats.get(powerUp);
        powerUpStats.replace(powerUp, oldCount + count);
    }

    /**
     * Update the use count for a specific special stone
     *
     * @param stone {@link SpecialStone } the special stone for which we want to increase the count
     * @param count the amount by which we want the increase the use count
     */
    public void updateSpecialStoneUseCount(SpecialStone stone, int count) {
        int oldCount = specialStoneStats.get(stone);
        specialStoneStats.replace(stone, oldCount + count);
    }

    /**
     * Get the amount of times a power up has been used
     *
     * @param powerUp the power up we want to get the use count
     **/
    public int getPowerUpUseCount(PowerUp powerUp) {
        return powerUpStats.get(powerUp);
    }

    /**
     * Get the amount of times a special stone has been used
     *
     * @param stone the special stone we want to get the use count
     **/
    public int getSpecialStoneUseCount(SpecialStone stone) {
        return specialStoneStats.get(stone);
    }


    /***
     Get the last five logged-in users
     * @return a list of the usernames of  the last five logged-in users
     * **/
    public List<String> getLastFiveLogins() {
        // get an instance of the user repository
        var userRepo = UserRepository.getInstance();
        // get all players
        var allPlayers = userRepo.getPlayers();
        // sort the players according to their last login date in reverse
        allPlayers.sort(Comparator.comparing(Player::getLastLogin).reversed());
        // extract the usernames of the top 5 players in the list and return them as a list
        return allPlayers.stream().map(User::getUsername).limit(5).collect(Collectors.toList());
    }

    /**
     * Write the contents of the stat manager (power up stats and special stone stats) to a file
     * called stats.json
     **/
    public void writeToFile() {
        try {
            // create an object mapper
            ObjectMapper objectMapper = new ObjectMapper();
            // create a new schema object
            StatSchema statSchema = new StatSchema(powerUpStats, specialStoneStats);
            // create a file
            File file = new File("stats.json");
            // if there is already a stats.json file existing
            if (file.exists() && !file.isDirectory()) {
                // delete the file
                file.delete();
            }
            // create a new file with the updated data
            objectMapper.writeValue(file, statSchema);
            // handle exceptions
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
