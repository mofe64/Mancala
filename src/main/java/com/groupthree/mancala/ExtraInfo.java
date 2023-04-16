package com.groupthree.mancala;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * version 1.0
 *
 *
 * @author Steve
 */
public class ExtraInfo {
    public static void display(String title){
        Stage window;
        ListView<String> mostFrequencyPlayer;
        Scene scene;

        window = new Stage();
        window.setTitle("Admin extra information");

        //Listview
        mostFrequencyPlayer = new ListView<>();
        mostFrequencyPlayer.getItems().addAll(
                "User A",
                "User B",
                "User C",
                "User D",
                "User E"
        );
        mostFrequencyPlayer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Button
        Button button = new Button("Back to Dashboard");
        button.setOnAction(e -> window.close());

        //Label
        Label label = new Label("Power-up and special stone frequency");

        //Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(mostFrequencyPlayer, button);

        //Set scene
        scene = new Scene(layout, 300, 400);
        window.setScene(scene);
        window.show();
    }
}
