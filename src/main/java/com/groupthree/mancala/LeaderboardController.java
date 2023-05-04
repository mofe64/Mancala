package com.groupthree.mancala;

import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.repository.UserRepository;
import com.groupthree.mancala.util.ApplicationContextManager;
import com.groupthree.mancala.util.LeaderboardProfileCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


/**
 * The LeaderboardController class manages the leaderboard screen, which displays a list of players in
 * descending order of their scores. It allows the logged-in player to take a screenshot of the screen
 * and go back to the previous screen.
 *
 * @author mofe
 * @version 1.0
 */
public class LeaderboardController {

    @FXML
    Button backBtn;

    @FXML
    Button shareButton;

    @FXML
    ListView<Player> leaderboardContainer;

    @FXML
    AnchorPane root;

    private String playerUsername;


    /**
     * Initializes the leaderboard screen with the username of the logged-in player, retrieves the player's
     * data from the UserRepository, and populates the leaderboard with data from the leaderboard database.
     *
     * @param username the username of the logged-in player.
     */
    public void initializeScreen(String username) {
        this.playerUsername = username;
        var loggedInPlayer = UserRepository.getInstance().getPlayer(username);
        ObservableList<Player> rankings = FXCollections.observableArrayList();
        var data = UserRepository.getInstance().getPlayerRankings();
        System.out.println("data is " + data);
        rankings.addAll(data);
        leaderboardContainer.setItems(rankings);
        leaderboardContainer.setCellFactory(param -> new LeaderboardProfileCell(loggedInPlayer));
    }


    /**
     * Navigates back to the previous screen when the back button is pressed.
     */
    @FXML
    private void goBack() {
        try {
            var context = ApplicationContextManager.getInstance();
            var previousStage = context.getPreviousStage();
            var previousView = context.getPreviousView();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(previousView));
            Parent root = loader.load();
            PlayerDashboardController controller = loader.getController();
            controller.setWelcomeText(playerUsername);
            Scene scene = new Scene(root);
            previousStage.setScene(scene);
            previousStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Takes a screenshot of the leaderboard screen and saves it as "screenshot.png". Displays an alert message
     * to inform the user that the screenshot was successful.
     */
    @FXML
    private void takeScreenShot() {
        WritableImage image = root.snapshot(new SnapshotParameters(), null);
        File file = new File("screenshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Screenshot successful");
            alert.setContentText("Screenshot has been taken successfully and saved as screenshot.png");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
