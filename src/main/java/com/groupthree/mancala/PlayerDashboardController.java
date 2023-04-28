package com.groupthree.mancala;

import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.repository.UserRepository;
import com.groupthree.mancala.util.ApplicationContextManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * PlayerDashboardController is a class that manages click actions on the player dashboard screen
 *
 * @author Yebo Ajakpo
 * @author Mofe
 * @version 2.0
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

    private String playerUsername;

    /**
     * On clicking the 'New Game Button' this will display the new game menu
     */
    public void setWelcomeText(String playerUsername) {
        this.playerUsername = playerUsername;
        welcomeText.setText("Welcome " + playerUsername);
    }

    @FXML
    protected void onNewGameButtonClick(ActionEvent event) {
        var userRepo = UserRepository.getInstance();
        MenuItem selectedItem = (MenuItem) event.getSource();
        if (selectedItem.getId().equalsIgnoreCase("arcade")) {
            System.out.println("Arcade mode selected ...");
        } else if (selectedItem.getId().equalsIgnoreCase("normal")) {
            System.out.println("normal mode selected ...");
            stage = (Stage) welcomeText.getScene().getWindow();

            Player player1 = userRepo.getPlayer(playerUsername);

            // Show pop up to get player2 details;
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            Label explanation = new Label("Enter the username of the second player");
            TextField usernameTextField = new TextField();
            Button startButton = new Button("Start Game");
            Label helper = new Label("Or");
            Button startGameWithComputer = new Button("Play against computer");
            VBox dialogVbox = new VBox(explanation, usernameTextField, startButton, helper, startGameWithComputer);
            dialogVbox.setSpacing(10);
            dialogVbox.setPadding(new Insets(20));
            dialogVbox.setAlignment(Pos.CENTER);
            startButton.setOnAction(e -> {
                var secondPlayerUsername = usernameTextField.getText();
                if (secondPlayerUsername == null || secondPlayerUsername.isEmpty()) {
                    var alert = new Alert(Alert.AlertType.ERROR, "Please provide the username for the second player");
                    alert.setTitle("Start Game Error");
                    alert.showAndWait();
                }
                var player2 = userRepo.getPlayer(secondPlayerUsername);
                if (player2 == null) {
                    var alert = new Alert(Alert.AlertType.ERROR, "No Player found with that username");
                    alert.setTitle("Start Game Error");
                    alert.showAndWait();
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("game-view.fxml"));
                        root = loader.load();
                        GameController gameController = loader.getController();
                        gameController.initializeNewGame(player1, player2);
                        stage = (Stage) welcomeText.getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        dialog.close();
                        stage.show();

                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                        var alert = new Alert(Alert.AlertType.ERROR, "Something went wrong please try again");
                        alert.setTitle("Start Game Error");
                        alert.showAndWait();
                    }
                }
            });
            startGameWithComputer.setOnAction(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("game-view.fxml"));
                    root = loader.load();
                    GameController gameController = loader.getController();
                    Player computer = new Player("computer", "", "", "");
                    gameController.initializeNewGame(player1, computer);
                    stage = (Stage) welcomeText.getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    dialog.close();
                    stage.show();

                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    var alert = new Alert(Alert.AlertType.ERROR, "Something went wrong please try again");
                    alert.setTitle("Start Game Error");
                    alert.showAndWait();
                }
            });

            Scene dialogScene = new Scene(dialogVbox, 300, 300);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }

    /**
     * On clicking the 'Profile Button' this will display the player's profile screen
     */
    @FXML
    protected void onProfileButtonClick(ActionEvent event) throws IOException {
        System.out.println("on profile clicked ...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("player-profile-view.fxml"));
        root = loader.load();
        PlayerProfileController profileController = loader.getController();
        profileController.setUpProfile(playerUsername);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        var context = ApplicationContextManager.getInstance();
        context.addStage(stage);
        context.addView("player-dashboard-view.fxml");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /**
     * On clicking the 'Leader Board Button' this will display the Leader board screen
     */
    @FXML
    protected void onLeaderboardButtonClick(ActionEvent event) throws IOException {
        System.out.println("on leaderboard clicked ...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("leaderboard-view.fxml"));
        root = loader.load();
        LeaderboardController controller = loader.getController();
        controller.initializeScreen(playerUsername);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        var context = ApplicationContextManager.getInstance();
        context.addStage(stage);
        context.addView("player-dashboard-view.fxml");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
