package com.hexapawn.hexapawn;

import javafx.scene.control.Button;

public class Controller {
    private final Board board;
    private final View view;
    private int currentPlayer = 1;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean gameOver = false;

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

    private void handlePawnSelection(Pawn pawn, int row, int col) {
        if (pawn != null && pawn.getPlayer() == currentPlayer) {
            selectedRow = row;
            selectedCol = col;
            view.highlightButton(row, col);

            System.out.println("Selected pawn at (" + row + ", " + col + ")");
        } else {
            System.out.println("You must select a pawn first");
        }
    }

    private void handlePawnMove(int row, int col) {
        if (isValidMove(selectedRow, selectedCol, row, col)) {
            board.movePawn(selectedRow, selectedCol, row, col);
            view.updateButton(selectedRow, selectedCol, null);
            view.updateButton(row, col, board.getPawn(row, col));
            System.out.println("Moved to (" + row + ", " + col + ")");

            GameState state = board.evaluateGameState(currentPlayer);
            if (state == GameState.WIN) {
                view.showWinner(currentPlayer);
                gameOver = true;
            } else if (state == GameState.LOSS) {
                view.showWinner(3 - currentPlayer);
                gameOver = true;
            } else {
                currentPlayer = 3 - currentPlayer ;
                view.showNextPlayer(currentPlayer);
            }

        } else {
            System.out.println("Cannot move to non-empty spot!");
        }

        view.clearHighlights();
        selectedRow = -1;
        selectedCol = -1;
    }

    private void handleClick(int row, int col) {
        if (gameOver) {
            return;
        }
        Pawn clickedPawn = board.getPawn(row, col);

        if (selectedRow == -1 && selectedCol == -1) {
            handlePawnSelection(clickedPawn, row, col);
        } else {
            handlePawnMove(row, col);
        }
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        Pawn pawn = board.getPawn(fromRow, fromCol);
        if (pawn == null) {
            return false;
        }

        int direction = (pawn.getPlayer() == 1) ? -1 : 1;
        int rowDiff = toRow - fromRow;
        int colDiff = Math.abs(toCol - fromCol);

        Pawn target = board.getPawn(toRow, toCol);

        System.out.println("Moved to (" + toRow + ", " + toCol + ")");
        System.out.println("Direction: " + direction + ", RowDiff: " + rowDiff + ", ColDiff: " + colDiff);

        // Forward move to empty square
        if (colDiff == 0 && rowDiff == direction && target == null) {
            return true;
        }

        // Diagonal capture
        return colDiff == 1
                && rowDiff == direction
                && target != null
                && target.getPlayer() != pawn.getPlayer();
    }
}
