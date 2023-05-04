package com.groupthree.mancala.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupthree.mancala.exceptions.ApplicationException;
import com.groupthree.mancala.exceptions.NotFoundException;
import com.groupthree.mancala.exceptions.UserExistsException;
import com.groupthree.mancala.models.*;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A utility class which acts as a repository for all users within the application
 * This class is a singleton and as such there will ever be one instance of the class
 * within the lifetime of the application
 *
 * @author mofe
 * @version 1.0
 **/
public class UserRepository {

    private static UserRepository INSTANCE;
    private final List<Player> players;
    private final List<Admin> admins;

    // private constructor in order to implement the singleton design pattern
    private UserRepository() {
        // when an object is created, we first try to restore the values from an existing database.json file
        // this file serves as file store for all the game stats
        try {
            // create file object
            File f = new File("database.json");
            // if the database.json file exists
            if (f.exists() && !f.isDirectory()) {

                System.out.println("Found database file");
                // create an object mapper to deserialize the json file
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                // read json into our stat schema object
                Schema loadedSchema = objectMapper.readValue(new File("database.json"), Schema.class);
                // initialize the players and admins variables
                players = loadedSchema.getPlayers();
                admins = loadedSchema.getAdmins();
                System.out.println("Initialized players and admins to database file content");
            } else {
                // if database.json file does not exist
                System.out.println("No database file found, initializing empty lists ...");
                // initialise instance variables to empty lists
                players = new ArrayList<>();
                admins = new ArrayList<>();
            }
            // handle exception
        } catch (Exception e) {
            String message = "Could not initialize database due to \n" + e.getMessage();
            throw new ApplicationException(message);
        }

    }

    /**
     * Get an instance of our user repository
     **/
    public static UserRepository getInstance() {
        // if an instance of this object has not been created yet, then create a new one
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    /**
     * Approve a new player into the system
     *
     * @param username : the username of the player we want to approve
     */
    public void approvePlayer(String username) {
        Player player = getPlayer(username);
        if (player == null) {
            throw new NotFoundException("No player found with username " + username);
        }
        player.setApproved(true);
    }

    /**
     * Retrieve the public profiles of all players who have not been approved
     *
     * @return A list of {@link PublicInfo} player profiles
     */
    public List<PublicInfo> getAllPlayersPendingApproval() {
        return players.stream()
                .filter(player -> !player.isApproved())
                .map(player -> player.getProfile().getPublicProfile())
                .collect(Collectors.toList());
    }

    /**
     * get an ordered list of players
     * The players are ordered by their number of wins
     **/
    public List<Player> getPlayerRankings() {
        var allPlayers = this.players;
        allPlayers.sort(Comparator.comparing(
                player -> player.getRecord().getNumberOfWins(),
                Comparator.reverseOrder())
        );
        return allPlayers;
    }

    /**
     * Update the details of a player
     *
     * @param username      the username of the player we want to update.
     * @param updatedPlayer : a player object containing the updated details of the player
     **/
    public void updatePlayer(String username, Player updatedPlayer) {

        Player player = getPlayer(username);
        if (player == null) {
            throw new NotFoundException("No Player found with username: " + username);
        }
        players.remove(player);
        players.add(updatedPlayer);
    }


    /**
     * Write the contents of the user repository (users and admins) to a file
     * called database.json
     **/
    public void writeToFile() {
        try {
            // create an object mapper
            ObjectMapper objectMapper = new ObjectMapper();
            // create a new schema object
            Schema applicationSchema = new Schema(players, admins);
            // create a file
            File file = new File("database.json");
            // if there is already a database.json file existing
            if (file.exists() && !file.isDirectory()) {
                // delete the file
                file.delete();
            }
            // create a new file with the updated data
            objectMapper.writeValue(file, applicationSchema);
            // handle exceptions
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Get a list of all players within the system
     **/
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Get a specific player within the system
     *
     * @param playerUsername : the username of the player we want to get
     **/
    public Player getPlayer(String playerUsername) {
        return players.stream().filter(player ->
                        player.getUsername().equalsIgnoreCase(playerUsername))
                .findFirst().orElse(null);
    }

    /**
     * Get a specific admin within the system
     *
     * @param adminUsername : the username of the admin we want to get
     **/
    public Admin getAdmin(String adminUsername) {
        return admins.stream().filter(player ->
                        player.getUsername().equalsIgnoreCase(adminUsername))
                .findFirst().orElse(null);
    }

    /**
     * Save a new player in the system
     *
     * @param player a player object containing all details of the player
     * @throws UserExistsException : if a player with the provided username already exists
     **/
    public void savePlayer(Player player) {
        Player existingPlayer = getPlayer(player.getUsername());
        if (existingPlayer != null) {
            String errorMessage = MessageFormat
                    .format("Player with username:{0} already exists",
                            existingPlayer.getUsername());
            throw new UserExistsException(errorMessage);
        }

        players.add(player);
    }

    /**
     * Save a new admin in the system
     *
     * @param admin an admin object containing all details of the player
     * @throws UserExistsException : if an admin with the provided username already exists
     **/
    public void saveAdmin(Admin admin) {
        Admin existingAdmin = getAdmin(admin.getUsername());
        if (existingAdmin != null) {
            String errorMessage = MessageFormat
                    .format("Player with username:{0} already exists",
                            existingAdmin.getUsername());
            throw new UserExistsException(errorMessage);
        }

        admins.add(admin);
    }

    /**
     * Get all admins currently in the system
     **/
    public List<Admin> getAdmins() {
        return admins;
    }


}
