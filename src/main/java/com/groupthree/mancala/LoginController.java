package com.groupthree.mancala;

import com.groupthree.mancala.repository.UserRepository;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    TextField username;

    @FXML
    Button loginButton;

    @FXML
    public void attemptLogin(ActionEvent event) throws IOException {
        String usernameValue = username.getText();
        var userRepo = UserRepository.getInstance();
        var player = userRepo.getPlayer(usernameValue);
        if (player != null) {
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
            System.out.println("found admin");
            return;
        }
        System.out.println(" not found display error ");

    }

    @FXML
    public void goToRegister() {
        System.out.println("going to register ...");
    }
}
