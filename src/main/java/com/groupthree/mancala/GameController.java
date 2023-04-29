package com.groupthree.mancala;

import com.groupthree.mancala.gameplay.*;
import com.groupthree.mancala.models.Player;
import com.groupthree.mancala.repository.StatManager;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    @FXML
    private Label row1hole1Value;
    @FXML
    private Label row1hole2Value;
    @FXML
    private Label row1hole3Value;
    @FXML
    private Label row1hole4Value;
    @FXML
    private Label row1hole5Value;
    @FXML
    private Label row1hole6Value;
    @FXML
    private Label row2hole1Value;
    @FXML
    private Label row2hole2Value;
    @FXML
    private Label row2hole3Value;
    @FXML
    private Label row2hole4Value;
    @FXML
    private Label row2hole5Value;
    @FXML
    private Label row2hole6Value;

    @FXML
    private Circle row1Hole1;
    @FXML
    private Circle row1Hole2;
    @FXML
    private Circle row1Hole3;
    @FXML
    private Circle row1Hole4;
    @FXML
    private Circle row1Hole5;
    @FXML
    private Circle row1Hole6;
    @FXML
    private Circle row2Hole1;
    @FXML
    private Circle row2Hole2;
    @FXML
    private Circle row2Hole3;
    @FXML
    private Circle row2Hole4;
    @FXML
    private Circle row2Hole5;
    @FXML
    private Circle row2Hole6;

    @FXML
    private Label playerOneUsername;

    @FXML
    private Rectangle playerOneStore;
    @FXML
    private Label playerOneStoreValue;

    @FXML
    private Rectangle payerTwoStore;
    @FXML
    private Label playerTwoStoreValue;

    @FXML
    private Label playerTwoUsername;

    @FXML
    private Button endGame;

    @FXML
    private Button playerOneCTPowerUp;
    @FXML
    private Button playerTwoCTPowerUp;
    @FXML
    private Button playerOneDPPowerUp;
    @FXML
    private Button playerTwoDPPowerUp;

    private List<Circle> topRow;
    private List<Circle> bottomRow;

    private Player player1;
    private Player player2;
    private Board board;

    private boolean isArcade;
    private boolean playerOneAppliedDoublePoints;
    private boolean playerOneAppliedContinueTurn;
    private boolean playerTwoAppliedDoublePoints;
    private boolean playerTwoAppliedContinueTurn;

    public GameController() {
        this.board = new Board();
    }

    @FXML
    private void moveMade(MouseEvent event) {
        Circle hole = (Circle) event.getSource();
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
        System.out.println("before calling continue turn " + board.isPlayerOneTurn());
        if (playerOneAppliedDoublePoints) {
            doublePoints(1);
            updateStones();
        }
        if (playerOneAppliedContinueTurn) {
            continueTurn(1);
            updateScene();
        }
        if (!board.isPlayerOneTurn() && player2.getUsername().equalsIgnoreCase("computer")) {
            makeComputerMove();
        }
        if (playerTwoAppliedDoublePoints) {
            doublePoints(2);
            updateStones();
        }
        if (playerTwoAppliedContinueTurn) {
            continueTurn(2);
            updateScene();
        }
        System.out.println("after calling continue turn " + board.isPlayerOneTurn());

    }

    private void makeComputerMove() {
        playerTwoUsername.setText("Computer Playing ... ");
        Random random = new Random();
        List<Integer> possibleMoves = new ArrayList<>();
        var bottomGameRow = board.getGameRows()[1];
        for (int i = 0; i < bottomGameRow.length; i++) {
            var hole = bottomGameRow[i];
            if (!hole.isEmpty()) {
                possibleMoves.add(i);
            }
        }

        if (possibleMoves.size() == 0) {
            return;
        }

        var num = random.nextInt(possibleMoves.size() - 1 + 1) + 1;
        var validIndex = possibleMoves.get(num - 1);
        var randomSpot = bottomRow.get(validIndex);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            Event.fireEvent(randomSpot, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
            playerTwoUsername.setText("Computer");
        });
        delay.play();
    }


    private void checkIfWinnerAndUpdateRecord() {
        if (board.gameOver()) {
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
            alert.show();
            updatePlayerRecords(winner);
            topRow.forEach(hole -> {
                hole.setDisable(true);
                hole.setOpacity(0.2);
            });
            bottomRow.forEach(hole -> {
                hole.setDisable(true);
                hole.setOpacity(0.3);
            });
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

    @FXML
    private void playerOneDoublePoints() {
        ArcadeBoard arcadeBoard = (ArcadeBoard) this.board;
        if (arcadeBoard.getPowerUpStatus(PowerUp.DOUBLE_POINTS, 2)) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Power Up used - Double points");
        alert.setContentText("Double points will be applied to player 1's next move");
        alert.showAndWait();
        playerOneAppliedDoublePoints = true;
    }

    @FXML
    private void playerTwoDoublePoints() {
        ArcadeBoard arcadeBoard = (ArcadeBoard) this.board;
        if (arcadeBoard.getPowerUpStatus(PowerUp.DOUBLE_POINTS, 2)) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Power Up used - Double points");
        alert.setContentText("Double points will be applied to player 2's next move");
        alert.showAndWait();
        playerTwoAppliedDoublePoints = true;
    }

    @FXML
    private void playerOneContinueTurn() {
        ArcadeBoard arcadeBoard = (ArcadeBoard) this.board;
        if (arcadeBoard.getPowerUpStatus(PowerUp.CONTINUE_TURN, 1)) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Power Up used - Continue turn");
        alert.setContentText("Player 1 will get another turn");
        alert.showAndWait();
        playerOneAppliedContinueTurn = true;
    }

    @FXML
    private void playerTwoContinueTurn() {
        ArcadeBoard arcadeBoard = (ArcadeBoard) this.board;
        if (arcadeBoard.getPowerUpStatus(PowerUp.CONTINUE_TURN, 2)) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Power Up used - Continue turn");
        alert.setContentText("Player 2 will get another turn");
        alert.showAndWait();
        playerTwoAppliedContinueTurn = true;

    }

    private void doublePoints(int playerNumber) {
        if (!isArcade) {
            return;
        }

        ArcadeBoard arcadeBoard = (ArcadeBoard) this.board;
        if (arcadeBoard.getPowerUpStatus(PowerUp.DOUBLE_POINTS, playerNumber)) {
            return;
        }
        var pointsGained = this.board.getTurnPoints();
        System.out.println("points gained " + pointsGained);
        String message = "";
        var statManager = StatManager.getInstance();
        if (playerNumber == 1) {
            arcadeBoard.boostPointsInStore(1, pointsGained);
            message = "player 1's " + pointsGained + "points doubled";
            arcadeBoard.usePowerUp(PowerUp.DOUBLE_POINTS, 1);
            statManager.updatePowerUpUseCount(PowerUp.DOUBLE_POINTS, 1);
        }

        if (playerNumber == 2) {
            arcadeBoard.boostPointsInStore(2, pointsGained);
            message = "player 2's " + pointsGained + "points doubled";
            arcadeBoard.usePowerUp(PowerUp.DOUBLE_POINTS, 2);
            statManager.updatePowerUpUseCount(PowerUp.DOUBLE_POINTS, 1);
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(message);
        a.show();
        board.resetTurnPoints();
    }

    private void continueTurn(int playerNumber) {
        if (!isArcade) {
            return;
        }
        ArcadeBoard arcadeBoard = (ArcadeBoard) this.board;
        if (arcadeBoard.getPowerUpStatus(PowerUp.CONTINUE_TURN, playerNumber)) {
            return;
        }
        String message = "";
        var statManager = StatManager.getInstance();
        if (playerNumber == 1) {
            arcadeBoard.setPlayerOneTurn(true);
            message = "player 1 extra turn";
            arcadeBoard.usePowerUp(PowerUp.CONTINUE_TURN, 1);
            statManager.updatePowerUpUseCount(PowerUp.CONTINUE_TURN, 1);
        }
        if (playerNumber == 2) {
            arcadeBoard.setPlayerOneTurn(false);
            message = "player 2 extra turn";
            arcadeBoard.usePowerUp(PowerUp.CONTINUE_TURN, 2);
            statManager.updatePowerUpUseCount(PowerUp.CONTINUE_TURN, 1);
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(message);
        a.show();
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
            playerOneDPPowerUp.setDisable(false);
            playerOneCTPowerUp.setDisable(false);
            playerTwoDPPowerUp.setDisable(true);
            playerTwoCTPowerUp.setDisable(true);
            inactiveRow = bottomRow;
            activeRow = topRow;
            active = 0;
        } else {
            playerOneDPPowerUp.setDisable(true);
            playerOneCTPowerUp.setDisable(true);
            playerTwoDPPowerUp.setDisable(false);
            playerTwoCTPowerUp.setDisable(false);
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
        if (isArcade) {
            ArcadeBoard arcadeBoard = (ArcadeBoard) this.board;
            if (arcadeBoard.getPowerUpStatus(PowerUp.DOUBLE_POINTS, 1)) {
                playerOneDPPowerUp.setDisable(true);
            }

            if (arcadeBoard.getPowerUpStatus(PowerUp.DOUBLE_POINTS, 2)) {
                playerTwoDPPowerUp.setDisable(true);
            }

            if (arcadeBoard.getPowerUpStatus(PowerUp.CONTINUE_TURN, 1)) {
                playerOneCTPowerUp.setDisable(true);
            }

            if (arcadeBoard.getPowerUpStatus(PowerUp.CONTINUE_TURN, 2)) {
                playerTwoCTPowerUp.setDisable(true);
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

    public void initializeNewGame(Player player1, Player player2, boolean isArcade) {
        endGame.setVisible(false);
        this.isArcade = isArcade;
        if (!isArcade) {
            playerOneCTPowerUp.setVisible(false);
            playerOneDPPowerUp.setVisible(false);
            playerTwoCTPowerUp.setVisible(false);
            playerTwoDPPowerUp.setVisible(false);
        } else {
            this.board = new ArcadeBoard();
        }
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
