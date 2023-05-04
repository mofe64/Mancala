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
 *
 * @author Mofe
 * @version 1.0
 */
public abstract class FavoriteProfileCell extends ListCell<PublicInfo> {
    private final Label name;
    private final HBox hBox;

    /**
     * Constructs a FavoriteProfileCell object.
     *
     * @param player the Player object associated with the cell
     */
    public FavoriteProfileCell(Player player) {
        super();
        // create a remove button in the cell
        Button removeButton = new Button("Remove");
        // whenever the remove button is clicked
        removeButton.setOnAction(event -> {
            // retrieve the public profile currently being displayed
            PublicInfo info = getItem();
            // remove the public profile the player's favourites list
            player.removeFromFavorites(info);
            System.out.println("Action: " + getItem());
            // call the update list method to update the items in the list view
            updateList();
            // update the user instance variable in the user repository
            UserRepository.getInstance().updatePlayer(player.getUsername(), player);
        });
        name = new Label();
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        hBox = new HBox(name, region, removeButton);
        setText(null);
    }

    /**
     * Updates the cell with the given item.
     *
     * @param item  the PublicInfo object to update the cell with
     * @param empty a boolean indicating whether the cell is empty or not
     */
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

    /**
     * Abstract method that must be implemented in subclasses to update the list.
     */
    public abstract void updateList();
}
