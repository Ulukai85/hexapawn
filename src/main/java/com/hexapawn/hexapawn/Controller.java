package com.hexapawn.hexapawn;

import javafx.scene.control.Button;

public class Controller {
    private final Board board;
    private final View view;
    private int currentPlayer = 1;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public Controller(Board board, View view) {
        this.board = board;
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                final int r = row;
                final int c = col;
                Button button = view.getButton(row, col);
                button.setOnAction(event -> handleClick(r, c));
            }
        }
    }

    private void handleClick(int r, int c) {
        System.out.println("Clicked: " + c + ", " + r);
    }
}
