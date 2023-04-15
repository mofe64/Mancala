package com.groupthree.mancala;

import com.groupthree.mancala.models.PublicInfo;
import com.groupthree.mancala.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PlayerProfileController {

    @FXML
    Text username;

    @FXML
    Text noOfGames;
    @FXML
    Text winPercentage;
    @FXML
    VBox profileRight;

    public void setUpProfile(String username) {
        var userRepo = UserRepository.getInstance();
        var player = userRepo.getPlayer(username);
        var playerRecord = player.getRecord();
        this.username.setText(username);
        this.noOfGames.setText(String.valueOf(playerRecord.getNumberOfGames()));
        double winPercentage = playerRecord.getNumberOfGames() == 0 ? 0.0
                : (double) (playerRecord.getNumberOfWins() * 100) / playerRecord.getNumberOfGames();

        this.winPercentage.setText(String.valueOf(winPercentage));
        var playerFavorites = player.getFavorites();
        ObservableList<PublicInfo> favoritesList = FXCollections.observableArrayList();
        favoritesList.addAll(playerFavorites);
        ListView<PublicInfo> favouritesListView = new ListView<>(favoritesList);
        favouritesListView.setCellFactory(param -> new ProfileCell(player) {
            @Override
            public void updateList() {
                var profile = getItem();
                favouritesListView.getItems().remove(profile);

            }
        });
        profileRight.setPadding(new Insets(10, 10, 10, 10));
        profileRight.getChildren().remove(profileRight.getChildren().size() - 1);
        profileRight.getChildren().add(favouritesListView);
    }

}
