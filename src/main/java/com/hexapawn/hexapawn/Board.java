package com.hexapawn.hexapawn;

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
        for (int col = 0; col < cols; col++) {
            board[col][rows - 1] = new Pawn(1);
            board[col][0] = new Pawn(2);
        }
    }

    public Pawn getPawn(int row, int col) {
        return board[row][col];
    }

    public void setPawn(int row, int col, Pawn pawn) {
        board[row][col] = pawn;
    }

    public void movePawn(int fromRow, int fromCol, int toRow, int toCol) {
        Pawn pawn = getPawn(fromRow, fromCol);
        setPawn(toRow, toCol, pawn);
        setPawn(fromRow, fromCol, null);
    }



    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
