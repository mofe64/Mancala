package com.groupthree.mancala;

import com.groupthree.mancala.repository.StatManager;
import com.groupthree.mancala.repository.UserRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/***
 * The driver class for the application
 * contains the start method which launches the application
 * @author mofe, yebo
 * */
public class MancalaApplication extends Application {
    /**
     * The start method is used to initialize the application.
     * It loads the start view and stage
     *
     * @param stage the stage for the application
     **/
    @Override
    public void start(Stage stage) throws IOException {
        UserRepository.getInstance();
        StatManager.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(MancalaApplication.class.getResource("start-screen-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Group 3");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The stop method is called whenever the application is closing
     * This method is used to write the content of our user repo and stat manager to their
     * respective files
     **/
    @Override
    public void stop() {
        System.out.println("Stage is closing");
        var userRepo = UserRepository.getInstance();
        userRepo.writeToFile();
        var statManager = StatManager.getInstance();
        statManager.writeToFile();
    }

    /***
     * The main method for the application
     * */
    public static void main(String[] args) {
        launch();
    }
}
