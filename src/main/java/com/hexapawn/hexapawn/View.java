package com.hexapawn.hexapawn;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class View {
    private final GridPane gridPane = new GridPane();
    private final Board board;
    private final Button[][] buttons;

    public View(Board board) {
        this.board = board;
        buttons = new Button[board.getRows()][board.getCols()];
        drawBoard();
    }

    private void drawBoard() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Button button = new Button();

                if ((row + col) % 2 == 0) {
                    button.setStyle("-fx-background-color: lightgray; -fx-font-size: 40");
                } else {
                    button.setStyle("-fx-background-color: darkgray; -fx-font-size: 40");
                }

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

    public GridPane getGridPane() {
        return gridPane;
    }

    public Button getButton(int row, int col) {
        return buttons[row][col];
    }
}
