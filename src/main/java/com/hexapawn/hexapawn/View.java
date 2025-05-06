package com.hexapawn.hexapawn;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class View {
    private VBox root;
    private GridPane gridPane;
    private Board board;
    private Button[][] buttons;
    private Label statusLabel;
    private Button twoPlayerButton;
    private Button randomAIButton;
    private Button minimaxAIButton;

    public View(Board board) {
        this.board = board;
        buttons = new Button[board.getRows()][board.getCols()];

        root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        statusLabel = new Label("Player 1's turn");

        HBox modeButtons = new HBox(10);
        modeButtons.setAlignment(Pos.CENTER);

        twoPlayerButton = new Button("2-Player Game");
        randomAIButton = new Button("Random AI");
        minimaxAIButton = new Button("Minimax AI");

        modeButtons.getChildren().addAll(twoPlayerButton, randomAIButton, minimaxAIButton);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        drawBoard();

        root.getChildren().addAll(statusLabel, modeButtons, gridPane);
    }

    public VBox getRoot() {
        return root;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Button getButton(int row, int col) {
        return buttons[row][col];
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Button getTwoPlayerButton() {
        return twoPlayerButton;
    }

    public Button getRandomAIButton() {
        return randomAIButton;
    }

    public Button getMinimaxAIButton() {
        return minimaxAIButton;
    }

    public void showWinner(int player) {
        getStatusLabel().setText("Player " + player + " wins!");
    }

    public void showNextPlayer(int player) {
        getStatusLabel().setText("Player " + player + "'s turn");
    }

    private void setDefaultStyling(Button button, int row, int col) {
        if ((row + col) % 2 == 0) {
            button.setStyle("-fx-background-color: lightgray; -fx-font-size: 40");
        } else {
            button.setStyle("-fx-background-color: darkgray; -fx-font-size: 40");
        }
    }

    public void drawBoard() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Button button = new Button();

                setDefaultStyling(button, row, col);

                button.setPrefSize(100, 100);
                gridPane.add(button, col, row);
                buttons[row][col] = button;

                Pawn pawn = board.getPawn(row, col);
                updateButton(row, col, pawn);
            }
        }
    }

    public void updateButton(int row, int col, Pawn pawn) {
        Button button = buttons[row][col];
        if (pawn == null) {
            button.setText("");
        } else {
            button.setText(pawn.getPlayer() == 1 ? "♙" : "♟");
        }
    }

    public void highlightButton(int row, int col) {
        Button button = buttons[row][col];
        if (button != null) {
            button.setStyle(button.getStyle() + "; -fx-border-color: red; -fx-border-width: 3px;");
        }
    }

    public void clearHighlights() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Button button = buttons[row][col];
                if (button != null) {
                    setDefaultStyling(button, row, col);
                }
            }
        }
    }
}
