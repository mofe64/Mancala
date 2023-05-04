package com.groupthree.mancala;

import com.groupthree.mancala.models.PublicInfo;
import com.groupthree.mancala.repository.UserRepository;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * A custom ListCell for displaying and approving player requests in the Player Approval screen.
 *
 * @author mofe
 * @version 1.0
 */
public abstract class PlayerApprovalProfileCell extends ListCell<PublicInfo> {
    private final Label name;
    private final HBox hBox;

    /**
     * Constructor for the PlayerApprovalProfileCell class. Creates a new cell with an approve button and a label for the
     * player's name.
     */
    public PlayerApprovalProfileCell() {
        super();
        Button approveButton = new Button("Approve");
        approveButton.setOnAction(event -> {
            PublicInfo userInfo = getItem();
            UserRepository.getInstance().approvePlayer(userInfo.username());
            System.out.println("Action: " + getItem());
            updateList();
        });
        name = new Label();
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        hBox = new HBox(name, region, approveButton);
        setText(null);
    }

    /**
     * Updates the item in the cell with the given PublicInfo object.
     *
     * @param item  the PublicInfo object to be displayed in the cell
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
     * Abstract method to be implemented by subclasses that updates the list of player requests to be displayed in the
     * Player Approval screen.
     */
    public abstract void updateList();
}
