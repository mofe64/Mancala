package com.groupthree.mancala.util;

import com.groupthree.mancala.models.GamePlayRecord;
import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.models.PublicInfo;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.List;

public class LeaderboardProfileCell extends ListCell<Player> {
    private final Label name;
    private final HBox hBox;
    private final Label noOfGames;
    private final Label noOfWins;
    private final Label noOfLosses;

    private final Player loggedInUser;
    private final Button addToFavourites;
    private final List<String> loggedInUserFavourites;

    public LeaderboardProfileCell(Player loggedInUser) {
        super();
        this.loggedInUser = loggedInUser;
        loggedInUserFavourites = loggedInUser.getFavorites().stream().map(PublicInfo::username).toList();
        noOfGames = new Label();
        noOfWins = new Label();
        noOfLosses = new Label();
        HBox statsContainer = new HBox(noOfGames, noOfWins, noOfLosses);
        statsContainer.setSpacing(15);
        addToFavourites = new Button();
        addToFavourites.setOnAction(event -> {
            Player displayedPlayer = getItem();
            if (loggedInUserFavourites.contains(displayedPlayer.getUsername())) {
                loggedInUser.removeFromFavorites(displayedPlayer.getPublicProfile());
                addToFavourites.setText("Add to favourites");
            } else {
                loggedInUser.addToFavorites(displayedPlayer.getPublicProfile());
                addToFavourites.setText("Remove to favourites");
            }
            System.out.println("Action: " + getItem());

        });

        name = new Label();
        Region outerRegion = new Region();
        HBox.setHgrow(outerRegion, Priority.ALWAYS);
        Region outerRegion2 = new Region();
        HBox.setHgrow(outerRegion2, Priority.ALWAYS);
        hBox = new HBox();
        hBox.getChildren().addAll(name, outerRegion, statsContainer, outerRegion2, addToFavourites);
        hBox.setSpacing(10);
        setText(null);

    }

    @Override
    public void updateItem(Player item, boolean empty) {
        super.updateItem(item, empty);
        setEditable(false);
        if (item != null) {
            if (item.getUsername().equalsIgnoreCase(loggedInUser.getUsername())) {
                addToFavourites.setDisable(true);
            }
            if (loggedInUserFavourites.contains(item.getUsername())) {
                addToFavourites.setText("Remove from favorites");
            } else {
                addToFavourites.setText("Add to favourites");
            }
            GamePlayRecord playRecord = item.getRecord();
            noOfGames.setText("Games Played: " + playRecord.getNumberOfGames());
            noOfWins.setText("Games Won: " + playRecord.getNumberOfWins());
            noOfLosses.setText("Games Lost: " + playRecord.getNumberOfLosses());
            name.setText(item.getUsername());
            setGraphic(hBox);
        } else {
            setGraphic(null);
        }
    }


}
