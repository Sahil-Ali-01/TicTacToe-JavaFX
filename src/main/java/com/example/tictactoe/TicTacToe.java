package com.example.tictactoe;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private Button[][] buttons = new Button[3][3];
    private Label playerXScoreLabel, playerOScoreLabel;
    private int playerXScore = 0;
    private int playerOScore = 0;
    private boolean isPlayerXTurn = true;

    private BorderPane createContent() {
        BorderPane root = new BorderPane();
        // Add a border to the root pane via style
        root.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        // Header
        Label titleLabel = new Label("Tic Tac Toe");
        titleLabel.setStyle("-fx-font-size: 35px; -fx-font-weight: bold");
        // Center the title in the top pane
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);

        // Game board
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER); // center the grid pane
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setStyle("-fx-font-size: 24px; -fx-font-weight: bold");
                button.setOnAction(event -> buttonClicked(button));
                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }
        root.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        // Scoreboard
        HBox scoreBoard = new HBox(20);
        scoreBoard.setAlignment(Pos.CENTER); // center the scoreboard
        playerXScoreLabel = new Label("Player X: " + playerXScore);
        playerXScoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold");
        playerOScoreLabel = new Label("Player O: " + playerOScore);
        playerOScoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold");
        scoreBoard.getChildren().addAll(playerXScoreLabel, playerOScoreLabel);
        root.setBottom(scoreBoard);
        BorderPane.setAlignment(scoreBoard, Pos.CENTER);

        return root;
    }

    private void buttonClicked(Button button) {
        if ("".equals(button.getText())) {
            button.setText(isPlayerXTurn ? "X" : "O");
            checkWinner();
            isPlayerXTurn = !isPlayerXTurn;
        }
    }

    // Updates the score for the winning player.
    private void updateScore(String winner) {
        if (winner.equals("X")) {
            playerXScore++;
            playerXScoreLabel.setText("Player X: " + playerXScore);
        } else {
            playerOScore++;
            playerOScoreLabel.setText("Player O: " + playerOScore);
        }
    }

    // Resets the board by clearing all button texts.
    private void resetBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    // Checks all win conditions, including horizontal, vertical, and diagonal.
    private void checkWinner() {
        // Check horizontal win
        for (int row = 0; row < 3; row++) {
            if (!buttons[row][0].getText().isBlank() &&
                    buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                    buttons[row][1].getText().equals(buttons[row][2].getText())) {
                String winner = buttons[row][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        // Check vertical win
        for (int col = 0; col < 3; col++) {
            if (!buttons[0][col].getText().isBlank() &&
                    buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                    buttons[1][col].getText().equals(buttons[2][col].getText())) {
                String winner = buttons[0][col].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        // Check diagonal win (top-left to bottom-right)
        if (!buttons[0][0].getText().isBlank() &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText())) {
            String winner = buttons[0][0].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }

        // Check diagonal win (top-right to bottom-left)
        if (!buttons[0][2].getText().isBlank() &&
                buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText())) {
            String winner = buttons[0][2].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
        }
    }

    private void showWinnerDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Congratulations " + winner + "! You Won the Game");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}