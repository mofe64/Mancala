package com.groupthree.mancala;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.net.MalformedURLException;

public class PlayerProfileController {

    @FXML
    private TextField username;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private Text lastLogin;
    @FXML
    private Text noOfGames;
    @FXML
    private Text winPercentage;
    @FXML
    private VBox profileRight;
    @FXML
    private ImageView profileImage;

    private String playerUsername;

    @FXML
    Button back;

    public void setUpProfile(String username) {
        this.playerUsername = username;
        var userRepo = UserRepository.getInstance();
        var player = userRepo.getPlayer(username);
        var playerRecord = player.getRecord();
        var playerProfileImageUrl = player.getProfile().getProfileImage();

        this.username.setText(username);
        this.firstname.setText(player.getProfile().getFirstname());
        this.lastname.setText(player.getProfile().getLastname());


        if (!playerProfileImageUrl.isEmpty()) {
            try {
                var imageFile = new File(playerProfileImageUrl);
                if (imageFile.exists()) {
                    var x = imageFile.toURI().toURL().toExternalForm();
                    Image image = new Image(x);
                    profileImage.setImage(image);
                }
            } catch (Exception ignored) {
                System.out.println("Got here ...");
            }
        }

        this.noOfGames.setText("Games played: " + playerRecord.getNumberOfGames());
        //Todo (set win percentage to two decimal places)
        double winPercentage = playerRecord.getNumberOfGames() == 0 ? 0.0
                : (double) (playerRecord.getNumberOfWins() * 100) / playerRecord.getNumberOfGames();

        this.winPercentage.setText("Win percentage: " + winPercentage);

        lastLogin.setText("Last Login: " +
                player.getProfile().getLastLoggedIn().toString());

        var playerFavorites = player.getFavorites();
        ObservableList<PublicInfo> favoritesList = FXCollections.observableArrayList();
        favoritesList.addAll(playerFavorites);
        ListView<PublicInfo> favouritesListView = new ListView<>(favoritesList);
        favouritesListView.setCellFactory(param -> new FavoriteProfileCell(player) {
            @Override
            public void updateList() {
                var profile = getItem();
                favouritesListView.getItems().remove(profile);

            }
        });
        profileRight.setPadding(new Insets(10, 10, 10, 10));
        profileRight.setSpacing(10);
        profileRight.getChildren().remove(profileRight.getChildren().size() - 1);
        profileRight.getChildren().add(favouritesListView);

    }

    @FXML
    private void goBack() {
        try {
            var context = ApplicationContextManager.getInstance();
            var previousStage = context.getPreviousStage();
            var previousView = context.getPreviousView();


            FXMLLoader loader = new FXMLLoader(getClass().getResource(previousView));
            Parent root = loader.load();
            PlayerDashboardController controller = loader.getController();
            controller.setWelcomeText(playerUsername);
            Scene scene = new Scene(root);
            previousStage.setScene(scene);
            previousStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    private void updateUserDetails() {
        var playerDetails = UserRepository.getInstance().getPlayer(playerUsername);
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
            playerDetails.getProfile().setFirstname(updatedFirstname);
            playerDetails.getProfile().setLastname(updatedLastname);
            playerDetails.getProfile().setUsername(updatedUsername);
            this.playerUsername = updatedUsername;
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
        var admin = UserRepository.getInstance().getPlayer(playerUsername);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            var userProfileImagePath = selectedFile.getAbsolutePath();
            admin.getProfile().setProfileImage(userProfileImagePath);
            var x = selectedFile.toURI().toURL().toExternalForm();
            Image image = new Image(x);
            profileImage.setImage(image);
            var alert = new Alert(Alert.AlertType.INFORMATION, "Profile Image updated successfully");
            alert.setTitle("Success");
            alert.showAndWait();
        }
    }

}
