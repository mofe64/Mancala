package com.groupthree.mancala.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupthree.mancala.exceptions.ApplicationException;
import com.groupthree.mancala.exceptions.NotFoundException;
import com.groupthree.mancala.exceptions.UserExistsException;
import com.groupthree.mancala.models.Admin;
import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.models.Schema;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository INSTANCE;
    private final List<Player> players;
    private final List<Admin> admins;

    private UserRepository() {
        try {
            File f = new File("database.json");
            if (f.exists() && !f.isDirectory()) {

                System.out.println("Found database file");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                Schema loadedSchema = objectMapper.readValue(new File("database.json"), Schema.class);
                players = loadedSchema.getPlayers();
                admins = loadedSchema.getAdmins();
                System.out.println("Initialized players and admins to database file content");
            } else {
                System.out.println("No database file found, initializing empty lists ...");
                players = new ArrayList<>();
                admins = new ArrayList<>();
            }
        } catch (Exception e) {
            String message = "Could not initialize database due to \n" + e.getMessage();
            throw new ApplicationException(message);
        }

    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    public void updatePlayer(String username, Player updatedPlayer) {

        Player player = getPlayer(username);
        if (player == null) {
            throw new NotFoundException("No Player found with username: " + username);
        }
        players.remove(player);
        players.add(updatedPlayer);
    }

    public void updateAdmin(String username, Admin admin) {
    }


    public boolean writeToFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Schema applicationSchema = new Schema(players, admins);
            File file = new File("database.json");
            if (file.exists() && !file.isDirectory()) {
                file.delete();
            }
            objectMapper.writeValue(file, applicationSchema);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String playerUsername) {
        return players.stream().filter(player ->
                        player.getUsername().equalsIgnoreCase(playerUsername))
                .findFirst().orElse(null);
    }

    public Admin getAdmin(String adminUsername) {
        return admins.stream().filter(player ->
                        player.getUsername().equalsIgnoreCase(adminUsername))
                .findFirst().orElse(null);
    }

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

    public List<Admin> getAdmins() {
        return admins;
    }


}
