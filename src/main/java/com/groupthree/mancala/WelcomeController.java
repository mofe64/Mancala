package com.groupthree.mancala;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * WelcomeController is a class that manages click actions on the start screen
 *
 * @author Yebo Ajakpo
 * @version 1.0
 */
public class WelcomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * On clicking the 'Login Button' this will navigate to the login screen
     */
    @FXML
    public void login(ActionEvent event) throws IOException {
        System.out.println("login called");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On clicking the 'Sign Up Button' this will navigate to the register screen
     */
    @FXML
    public void register(ActionEvent event) throws IOException {
        System.out.println("register called");
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