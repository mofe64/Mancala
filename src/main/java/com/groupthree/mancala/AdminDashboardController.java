package com.groupthree.mancala;

import com.groupthree.mancala.models.Admin;
import com.groupthree.mancala.models.PublicInfo;
import com.groupthree.mancala.repository.UserRepository;
import com.groupthree.mancala.util.ApplicationContextManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the controller for Admin dashboard.
 *
 * @author Steve(Yingcong), Mofe
 */
public class AdminDashboardController {


    private Stage stage;
    private Scene scene;
    private Parent root;
    private String adminUsername;

    @FXML
    HBox playerApprovalContainer;


    @FXML
    Label welcomeText;


    /**
     * This is the method to handle the profile button.
     * When click on the profile button it will go to another window that shows user's profile
     *
     * @param event the {@link ActionEvent}
     * @throws IOException if the set fxml file cannot be found
     */
    @FXML
    public void onProfileButtonClick(ActionEvent event) throws IOException {
        // retrieve the admin object
        var admin = UserRepository.getInstance().getAdmin(adminUsername);
        System.out.println("on profile clicked ...");
        // create our loader from the next view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-profile-view.fxml"));
        root = loader.load();
        // retrieve the controller
        AdminProfileController profileController = loader.getController();
        // initialize the profile controller
        profileController.setUp(admin);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // retrieve our context from the context manager
        var context = ApplicationContextManager.getInstance();
        // add the current stage and view to our context, so we can navigate back to this page
        context.addStage(stage);
        context.addView("admin-dashboard-view.fxml");
        // change the scene and navigate to the next scene
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the method for handle the Game Stats button.
     * When click this button will go to the Game Stats scene
     *
     * @param event the {@link ActionEvent}
     * @throws IOException if the set fxml file cannot be found
     */
    @FXML
    public void onGameStatsClick(ActionEvent event) throws IOException {
        System.out.println("on stats clicked ...");
        // create our loader from the next view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-stats-view.fxml"));
        root = loader.load();
        // retrieve the controller
        StatViewController controller = loader.getController();
        // initialize the controller
        controller.initialize(adminUsername);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // retrieve our context from the context manager
        var context = ApplicationContextManager.getInstance();
        // add the current stage and view to our context, so we can navigate back to this page
        context.addStage(stage);
        context.addView("admin-dashboard-view.fxml");
        scene = new Scene(root);
        // change the scene and navigate to the next scene
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialise the admin dashboard view with the right elements and data
     * Method must be called before navigating to the admin dashboard screen
     *
     * @param adminName the username of the currently logged in admin
     */

    public void initializeScreen(String adminName) {
        Admin admin = UserRepository.getInstance().getAdmin(adminName);
        this.adminUsername = admin.getUsername();
        welcomeText.setText("Welcome " + adminName);
        //Label player list
        Label label = new Label("Players that need to be approved");


        ObservableList<PublicInfo> approvalsList = FXCollections.observableArrayList();
        var data = UserRepository.getInstance().getAllPlayersPendingApproval();
        System.out.println("data is " + data);
        approvalsList.addAll(data);
        ListView<PublicInfo> playersList = new ListView<>(approvalsList);
        playersList.setPrefSize(300, 170);
        playersList.setCellFactory(param -> new PlayerApprovalProfileCell() {
            @Override
            public void updateList() {
                var userProfile = getItem();
                playersList.getItems().remove(userProfile);
            }
        });

        //VBox layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(label, playersList);

        //Setting scene
        playerApprovalContainer.getChildren().add(layout);

    }

}