package com.hexapawn.hexapawn;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows;
    private final int cols;
    private Pawn[][] board;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new Pawn[rows][cols];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = null;
            }
        }

        for (int col = 0; col < cols; col++) {
            board[0][col] = new Pawn(2);
            board[rows - 1][col] = new Pawn(1);
        }
    }

    public void resetBoard() {
        initializeBoard();
    }

    public Pawn getPawn(int row, int col) {
        return board[row][col];
    }

    public void setPawn(int row, int col, Pawn pawn) {
        board[row][col] = pawn;
    }

    public void movePawn(Move move) {
        Pawn pawn = getPawn(move.fromRow, move.fromCol);
        setPawn(move.toRow, move.toCol, pawn);
        setPawn(move.fromRow, move.fromCol, null);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public GameState evaluateGameState(int currentPlayer) {
        if (hasPawnAtEnd(currentPlayer)) {
            return GameState.WIN;
        }
        if (hasNoMoves(3 - currentPlayer)) {
            return GameState.WIN;
        }
        if (hasPawnAtEnd(3 - currentPlayer)) {
            return GameState.LOSS;
        }
        if (hasNoMoves(currentPlayer)) {
            return GameState.LOSS;
        }
        return GameState.ONGOING;
    }

    private boolean hasPawnAtEnd(int player) {
        int targetRow = (player == 1) ? 0 : rows - 1;
        for (int col = 0; col < cols; col++) {
            Pawn pawn = getPawn(targetRow, col);
            if (pawn != null && pawn.getPlayer() == player) {
                return true;
            }
        }
        return false;
    }

    public List<Move> getAllPossibleMoves(int player) {
        List<Move> moves = new ArrayList<>();

        int direction = (player == 1) ? -1 : 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Pawn pawn = getPawn(row, col);
                if (pawn != null && pawn.getPlayer() == player) {
                    int nextRow = row + direction;

                    if (isInsideBoard(nextRow, col) && getPawn(nextRow, col) == null) {
                        moves.add(new Move(row, col, nextRow, col));
                    }

                    if (isInsideBoard(nextRow, col - 1)) {
                        Pawn target = getPawn(nextRow, col - 1);
                        if (target != null && target.getPlayer() != player) {
                            moves.add(new Move(row, col, nextRow, col - 1));
                        }
                    }

                    if (isInsideBoard(nextRow, col + 1)) {
                        Pawn target = getPawn(nextRow, col + 1);
                        if (target != null && target.getPlayer() != player) {
                            moves.add(new Move(row, col, nextRow, col + 1));
                        }
                    }
                }
            }
        }
        return moves;
    }

    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private boolean hasNoMoves(int player) {
        return getAllPossibleMoves(player).isEmpty();
    }
}
