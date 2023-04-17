package com.groupthree.mancala;

import com.groupthree.mancala.gameplay.Board;
import com.groupthree.mancala.gameplay.Hole;
import com.groupthree.mancala.gameplay.Store;
import com.groupthree.mancala.models.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    @FXML
    Label row1hole1Value;
    @FXML
    Label row1hole2Value;
    @FXML
    Label row1hole3Value;
    @FXML
    Label row1hole4Value;
    @FXML
    Label row1hole5Value;
    @FXML
    Label row1hole6Value;
    @FXML
    Label row2hole1Value;
    @FXML
    Label row2hole2Value;
    @FXML
    Label row2hole3Value;
    @FXML
    Label row2hole4Value;
    @FXML
    Label row2hole5Value;
    @FXML
    Label row2hole6Value;

    @FXML
    Circle row1Hole1;
    @FXML
    Circle row1Hole2;
    @FXML
    Circle row1Hole3;
    @FXML
    Circle row1Hole4;
    @FXML
    Circle row1Hole5;
    @FXML
    Circle row1Hole6;
    @FXML
    Circle row2Hole1;
    @FXML
    Circle row2Hole2;
    @FXML
    Circle row2Hole3;
    @FXML
    Circle row2Hole4;
    @FXML
    Circle row2Hole5;
    @FXML
    Circle row2Hole6;

    @FXML
    Label playerOneUsername;

    @FXML
    Rectangle playerOneStore;
    @FXML
    Label playerOneStoreValue;

    @FXML
    Rectangle payerTwoStore;
    @FXML
    Label playerTwoStoreValue;

    @FXML
    Label playerTwoUsername;

    @FXML
    Button endGame;

    private List<Circle> topRow;
    private List<Circle> bottomRow;

    private Player player1;
    private Player player2;
    private final Board board;
    //Todo fix game ends with user playing into store bug

    public GameController() {
        this.board = new Board();
    }

    @FXML
    private void moveMade(MouseEvent event) {
        Circle hole = (Circle) event.getSource();
        row2Hole1.setDisable(true);
        String id = hole.getId();
        switch (id) {
            case "row1Hole1", "row2Hole1" -> makeMove(1);
            case "row1Hole2", "row2Hole2" -> makeMove(2);
            case "row1Hole3", "row2Hole3" -> makeMove(3);
            case "row1Hole4", "row2Hole4" -> makeMove(4);
            case "row1Hole5", "row2Hole5" -> makeMove(5);
            case "row1Hole6", "row2Hole6" -> makeMove(6);
        }
        updateStones();
        updateScene();
        checkIfWinnerAndUpdateRecord();

    }


    private void checkIfWinnerAndUpdateRecord() {
        System.out.println("checking for winner");
        if (board.gameOver()) {
            System.out.println("winner found");
            String message = "";
            var winner = board.checkWinner();
            if (winner == 1) {
                message = player1.getUsername() + " Wins \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89";
            }
            if (winner == 2) {
                message = player2.getUsername() + " Wins \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89";
            }
            if (winner == 0) {
                message = "Looks like it's a draw \uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D";
            }

            var alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setTitle("Game Over");
            alert.showAndWait();
            updatePlayerRecords(winner);
            endGame.setVisible(true);
            endGame.setOnAction(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("player-dashboard-view.fxml"));
                    Parent root = loader.load();
                    PlayerDashboardController controller = loader.getController();
                    controller.setWelcomeText(player1.getUsername());
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception exception) {
                    var errorAlert = new Alert(Alert.AlertType.ERROR, "Looks like something went wrong," +
                            " please try again");
                    errorAlert.setTitle("End Game Error");
                    errorAlert.showAndWait();
                }

            });
        }
    }

    private void updatePlayerRecords(int winner) {
        var playerOneRecord = player1.getRecord();
        var playerTwoRecord = player2.getRecord();
        playerOneRecord.incrementGames();
        playerTwoRecord.incrementGames();
        if (winner == 1) {
            playerOneRecord.incrementWins();
            playerTwoRecord.incrementLosses();
        }

        if (winner == 2) {
            playerTwoRecord.incrementWins();
            playerOneRecord.incrementLosses();
        }
    }

    private void updateScene() {
        List<Circle> inactiveRow;
        List<Circle> activeRow;
        int active;
        if (board.isPlayerOneTurn()) {
            inactiveRow = bottomRow;
            activeRow = topRow;
            active = 0;
        } else {
            inactiveRow = topRow;
            activeRow = bottomRow;
            active = 1;
        }
        for (Circle circle : inactiveRow) {
            circle.setDisable(true);
            circle.setOpacity(0.3);
        }
        for (Circle circle : activeRow) {
            circle.setDisable(false);
            circle.setOpacity(1.0);
        }
        Hole[] activeHoles = board.getGameRows()[active];
        for (int i = 0; i < activeHoles.length; i++) {
            var currentHole = activeHoles[i];
            if (currentHole.isEmpty()) {
                Circle emptyCircle;
                if (active == 0) {
                    emptyCircle = topRow.get(i);
                } else {
                    emptyCircle = bottomRow.get(i);
                }
                emptyCircle.setDisable(true);
                emptyCircle.setOpacity(0.7);
            } else {
                Circle filledCircle;
                if (active == 0) {
                    filledCircle = topRow.get(i);
                } else {
                    filledCircle = bottomRow.get(i);
                }
                filledCircle.setDisable(false);
                filledCircle.setOpacity(1.0);
            }
        }
    }

    private void updateStones() {
        var row1 = board.getGameRows()[0];
        var row2 = board.getGameRows()[1];
        row1hole1Value.setText(String.valueOf(row1[0].checkStoneCount()));
        row1hole2Value.setText(String.valueOf(row1[1].checkStoneCount()));
        row1hole3Value.setText(String.valueOf(row1[2].checkStoneCount()));
        row1hole4Value.setText(String.valueOf(row1[3].checkStoneCount()));
        row1hole5Value.setText(String.valueOf(row1[4].checkStoneCount()));
        row1hole6Value.setText(String.valueOf(row1[5].checkStoneCount()));
        row2hole1Value.setText(String.valueOf(row2[0].checkStoneCount()));
        row2hole2Value.setText(String.valueOf(row2[1].checkStoneCount()));
        row2hole3Value.setText(String.valueOf(row2[2].checkStoneCount()));
        row2hole4Value.setText(String.valueOf(row2[3].checkStoneCount()));
        row2hole5Value.setText(String.valueOf(row2[4].checkStoneCount()));
        row2hole6Value.setText(String.valueOf(row2[5].checkStoneCount()));

        Store playerOneStore = board.getPlayer1Store();
        Store playerTwoStore = board.getPlayer2Store();

        playerOneStoreValue.setText(String.valueOf(playerOneStore.getNumberOfStonesInStore()));
        playerTwoStoreValue.setText(String.valueOf(playerTwoStore.getNumberOfStonesInStore()));


    }

    public void initializeNewGame(Player player1, Player player2) {
        endGame.setVisible(false);
        this.player1 = player1;
        this.player2 = player2;
        playerOneUsername.setText(player1.getUsername());
        playerTwoUsername.setText(player2.getUsername());
        topRow = new ArrayList<>();
        topRow.add(row1Hole1);
        topRow.add(row1Hole2);
        topRow.add(row1Hole3);
        topRow.add(row1Hole4);
        topRow.add(row1Hole5);
        topRow.add(row1Hole6);
        bottomRow = new ArrayList<>();
        bottomRow.add(row2Hole1);
        bottomRow.add(row2Hole2);
        bottomRow.add(row2Hole3);
        bottomRow.add(row2Hole4);
        bottomRow.add(row2Hole5);
        bottomRow.add(row2Hole6);
        updateStones();
        updateScene();
    }


    private void makeMove(int holeNumber) {
        System.out.println("make move called with " + holeNumber);
        board.moveStones(holeNumber);
    }


}
