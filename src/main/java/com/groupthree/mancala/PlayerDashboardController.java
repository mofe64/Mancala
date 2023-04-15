package com.groupthree.mancala;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * PlayerDashboardController is a class that manages click actions on the player dashboard screen
 *
 * @author Yebo Ajakpo
 * @version 1.0
 */
public class PlayerDashboardController {


    @FXML
    Label welcomeText;
    @FXML
    MenuItem arcade;
    @FXML
    MenuItem normal;


    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * On clicking the 'New Game Button' this will display the new game menu
     */
    public void setWelcomeText(String playerUsername) {
        welcomeText.setText("Welcome " + playerUsername);
    }

    @FXML
    protected void onNewGameButtonClick(ActionEvent event) throws IOException {
        MenuItem selectedItem = (MenuItem) event.getSource();
        if (selectedItem.getId().equalsIgnoreCase("arcade")) {
            System.out.println("Arcade mode selected ...");
        } else if (selectedItem.getId().equalsIgnoreCase("normal")) {
            System.out.println("normal mode selected ...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-view.fxml"));
            root = loader.load();

            GameController gameController = loader.getController();
            gameController.initializeNewGame();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * On clicking the 'Profile Button' this will display the player's profile screen
     */
    @FXML
    protected void onProfileButtonClick() {
        System.out.println("on profile clicked ...");
    }

    /**
     * On clicking the 'Leader Board Button' this will display the Leader board screen
     */
    @FXML
    protected void onLeaderboardButtonClick() {
        System.out.println("on leaderboard clicked ...");
    }
}
