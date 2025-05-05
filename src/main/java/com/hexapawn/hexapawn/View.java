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
                button.setMinSize(80, 80);
                updateButton(button, row, col);
                gridPane.add(button, row, col);
                buttons[row][col] = button;
            }
        }
    }

    private void updateButton(Button button, int row, int col) {
        Pawn pawn = board.getPawn(row, col);
        if (pawn == null) {
            button.setText("");
        } else {
            button.setText(pawn.getPlayer() == 1 ? "W" : "B");
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Button getButton(int row, int col) {
        return buttons[row][col];
    }
}
