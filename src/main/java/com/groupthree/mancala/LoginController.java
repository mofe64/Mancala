package com.groupthree.mancala;

import com.groupthree.mancala.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


/**
 * The LoginController class is responsible for handling the actions of the login screen,
 * such as attempting to log in the user and redirecting them to the appropriate dashboard.
 * It also provides a method for redirecting to the register screen.
 *
 * @author mofe
 * @version 1.0
 */
public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    TextField username;

    @FXML
    Button loginButton;

    /**
     * Attempts to log in the user with the provided username.
     * If a player or admin account with that username is found, the user is redirected to the appropriate dashboard.
     * If no user is found with the provided username, an error alert is displayed.
     *
     * @param event The action event that triggered the login attempt
     * @throws IOException if the FXML loader encounters an error while loading the dashboard view
     */
    @FXML
    public void attemptLogin(ActionEvent event) throws IOException {
        String usernameValue = username.getText();
        var userRepo = UserRepository.getInstance();
        var player = userRepo.getPlayer(usernameValue);
        if (player != null) {
            player.getProfile().setLastLoggedIn(LocalDate.now());
            System.out.println("found player");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-dashboard-view.fxml"));
            root = loader.load();
            PlayerDashboardController controller = loader.getController();
            controller.setWelcomeText(player.getUsername());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;
        }
        var admin = userRepo.getAdmin(usernameValue);
        if (admin != null) {
            admin.getProfile().setLastLoggedIn(LocalDate.now());
            System.out.println("found admin");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-dashboard-view.fxml"));
            root = loader.load();
            AdminDashboardController controller = loader.getController();
            controller.initializeScreen(admin.getUsername());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;
        }
        var alert = new Alert(Alert.AlertType.ERROR, "No user found with username: " + usernameValue);
        alert.setTitle("Login Error");
        alert.showAndWait();

    }

    /**
     * Redirects the user to the register screen.
     *
     * @param event The action event that triggered the redirection to the register screen
     * @throws IOException if the FXML loader encounters an error while loading the register view
     */
    @FXML
    public void goToRegister(ActionEvent event) throws IOException {
        System.out.println("going to register ...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register-view.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        RegisterController registerController = loader.getController();
        registerController.initializeScreen(stage);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
