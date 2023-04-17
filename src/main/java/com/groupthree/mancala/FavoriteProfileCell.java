package com.groupthree.mancala;

import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.models.PublicInfo;
import com.groupthree.mancala.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


/**
 * Profile Cell represents a custom list cell containing a users to be displayed within a list view
 * @author Mofe
 * @version 1.0
 */
public abstract class FavoriteProfileCell extends ListCell<PublicInfo> {
    private final Label name;
    private final HBox hBox;

    public FavoriteProfileCell(Player player) {
        super();
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> {
            PublicInfo info = getItem();
            player.removeFromFavorites(info);
            System.out.println("Action: " + getItem());
            updateList();
            UserRepository.getInstance().updatePlayer(player.getUsername(), player);
        });
        name = new Label();
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        hBox = new HBox(name, region, removeButton);
        setText(null);
    }
    @Override
    public void updateItem(PublicInfo item, boolean empty) {
        super.updateItem(item, empty);
        setEditable(false);
        if (item != null) {
            name.setText(item.username());
            setGraphic(hBox);
        } else {
            setGraphic(null);
        }
    }
    public abstract void updateList();
}
