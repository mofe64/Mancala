package com.groupthree.mancala;

import com.groupthree.mancala.gameplay.PowerUp;
import com.groupthree.mancala.gameplay.SpecialStone;
import com.groupthree.mancala.repository.StatManager;
import com.groupthree.mancala.util.ApplicationContextManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class StatViewController {
    @FXML
    private Label halfHand;
    @FXML
    private Label reverseTurn;
    @FXML
    private Label switchSides;
    @FXML
    private Label continueTurn;
    @FXML
    private Label doublePoints;
    @FXML
    private Label username1;
    @FXML
    private Label username2;
    @FXML
    private Label username3;
    @FXML
    private Label username4;
    @FXML
    private Label username5;
    private String adminUsername;

    public void initialize(String adminUsername) {
        this.adminUsername = adminUsername;
        var statManager = StatManager.getInstance();
        halfHand.setText("Half Hand : " + statManager.getSpecialStoneUseCount(SpecialStone.HALF_HAND));
        reverseTurn.setText("Reverse Turn : " + statManager.getSpecialStoneUseCount(SpecialStone.REVERSE_TURN));
        switchSides.setText("Switch Sides : " + statManager.getSpecialStoneUseCount(SpecialStone.SWITCH_SIDES));
        continueTurn.setText("Continue Turn : " + statManager.getPowerUpUseCount(PowerUp.CONTINUE_TURN));
        doublePoints.setText("Double Points : " + statManager.getPowerUpUseCount(PowerUp.DOUBLE_POINTS));
        username1.setAlignment(Pos.CENTER);
        List<Label> usernameLabels = new ArrayList<>();
        usernameLabels.add(username1);
        usernameLabels.add(username2);
        usernameLabels.add(username3);
        usernameLabels.add(username4);
        usernameLabels.add(username5);
        var lastFiveLogins = statManager.getLastFiveLogins();
        for (int i = 0; i < lastFiveLogins.size(); i++) {
            var username = lastFiveLogins.get(i);
            var label = usernameLabels.get(i);
            label.setText(username);
        }
    }


    public void goBack() {
        try {
            var context = ApplicationContextManager.getInstance();
            var previousStage = context.getPreviousStage();
            var previousView = context.getPreviousView();


            FXMLLoader loader = new FXMLLoader(getClass().getResource(previousView));
            Parent root = loader.load();
            AdminDashboardController controller = loader.getController();
            controller.initializeScreen(adminUsername);
            Scene scene = new Scene(root);
            previousStage.setScene(scene);
            previousStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


}
