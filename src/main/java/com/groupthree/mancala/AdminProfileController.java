package com.groupthree.mancala;

import com.groupthree.mancala.models.Admin;
import com.groupthree.mancala.repository.UserRepository;
import com.groupthree.mancala.util.ApplicationContextManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.net.MalformedURLException;

public class AdminProfileController {

    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;

    @FXML
    private TextField username;
    @FXML
    private Label lastLogin;

    @FXML
    private ImageView profilePicture;

    @FXML
    private Button profileImageButton;

    @FXML
    private Button updateProfileDetailsButton;

    private String adminUsername;


    public void setUp(Admin admin) {
        adminUsername = admin.getUsername();
        firstname.setText(admin.getProfile().getFirstname());
        lastname.setText(admin.getProfile().getLastname());
        username.setText(admin.getUsername());
        lastLogin.setText("Last login: " + admin.getProfile().getLastLoggedIn().toLocalDate().toString());
        var profileImageUrl = admin.getProfile().getProfileImage();
        if (!profileImageUrl.isEmpty()) {
            try {
                var imageFile = new File(profileImageUrl);
                if (imageFile.exists()) {
                    var x = imageFile.toURI().toURL().toExternalForm();
                    Image image = new Image(x);
                    profilePicture.setImage(image);
                }
            } catch (Exception ignored) {
                System.out.println("Got here ...");
            }

        }
    }

    @FXML
    private void updateUserDetails() {
        var adminDetails = UserRepository.getInstance().getAdmin(adminUsername);
        var updatedFirstname = firstname.getText();
        var updatedLastname = lastname.getText();
        var updatedUsername = username.getText();
        var validationResult = validate(updatedUsername, updatedFirstname, updatedLastname);
        var error = validationResult.getKey();
        if (error) {
            var errorMessage = validationResult.getValue();
            var alert = new Alert(Alert.AlertType.ERROR, errorMessage);
            alert.setTitle("Profile Update Error");
            alert.showAndWait();
        } else {
            adminDetails.getProfile().setFirstname(updatedFirstname);
            adminDetails.getProfile().setLastname(updatedLastname);
            adminDetails.getProfile().setUsername(updatedUsername);
            this.adminUsername = updatedUsername;
            var alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully");
            alert.setTitle("Success");
            alert.showAndWait();
        }

    }

    private Pair<Boolean, String> validate(String usernameValue, String firstnameValue, String lastnameValue) {
        var errorEncountered = false;
        var errorMessage = "";

        if (usernameValue == null || usernameValue.isEmpty()) {
            errorEncountered = true;
            errorMessage += "Please provide a valid username value";
            errorMessage += "\n";
        }

        if (firstnameValue == null || firstnameValue.isEmpty()) {
            errorEncountered = true;
            errorMessage += "Please provide a valid firstname value";
            errorMessage += "\n";
        }

        if (lastnameValue == null || lastnameValue.isEmpty()) {
            errorEncountered = true;
            errorMessage += "Please provide a valid lastname value";
            errorMessage += "\n";
        }
        return new Pair<>(errorEncountered, errorMessage);
    }

    @FXML
    private void updateProfileImage(ActionEvent event) throws MalformedURLException {
        var admin = UserRepository.getInstance().getAdmin(adminUsername);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            var userProfileImagePath = selectedFile.getAbsolutePath();
            admin.getProfile().setProfileImage(userProfileImagePath);
            var x = selectedFile.toURI().toURL().toExternalForm();
            Image image = new Image(x);
            profilePicture.setImage(image);
            var alert = new Alert(Alert.AlertType.INFORMATION, "Profile Image updated successfully");
            alert.setTitle("Success");
            alert.showAndWait();
        }
    }

    @FXML
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
