package com.groupthree.mancala;

import com.groupthree.mancala.models.PublicInfo;
import com.groupthree.mancala.repository.UserRepository;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public abstract class PlayerApprovalProfileCell extends ListCell<PublicInfo> {
    private final Label name;
    private final HBox hBox;

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
