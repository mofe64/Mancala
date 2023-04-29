package com.groupthree.mancala;

import com.groupthree.mancala.repository.StatManager;
import com.groupthree.mancala.repository.UserRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UserRepository.getInstance();
        StatManager.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-screen-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Group 3");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
        var userRepo = UserRepository.getInstance();
        userRepo.writeToFile();
        var statManager = StatManager.getInstance();
        statManager.writeToFile();
    }

    public static void main(String[] args) {
        launch();
    }
}
