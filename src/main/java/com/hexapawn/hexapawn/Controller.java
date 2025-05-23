package com.hexapawn.hexapawn;

import javafx.scene.control.Button;

import java.util.List;
import java.util.Random;

public class Controller {
    private final Board board;
    private final View view;
    private int currentPlayer = 1;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean gameOver = false;
    private boolean player2IsRandomAI = false;
    private boolean player2IsMiniMaxAI = false;
    private int maxDepth;

    public Controller(Board board, View view, int maxDepth) {
        this.board = board;
        this.view = view;
        this.maxDepth = maxDepth;
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
        view.getTwoPlayerButton().setOnAction(event -> startTwoPlayerGame());
        view.getRandomAIButton().setOnAction(event -> startRandomAIGame());
        view.getMinimaxAIButton().setOnAction(event -> startMinimaxAIGame());
    }

    private void setupNewGame() {
        currentPlayer = 1;
        gameOver = false;
        board.resetBoard();
        view.drawBoard();
        view.showNextPlayer(currentPlayer);
        player2IsRandomAI = false;
        player2IsMiniMaxAI = false;

        addEventHandlers();
    }

    private void startMinimaxAIGame() {
        setupNewGame();
        player2IsMiniMaxAI = true;
    }

    private void startRandomAIGame() {
        setupNewGame();
        player2IsRandomAI = true;
    }

    private void startTwoPlayerGame() {
        setupNewGame();

    }

    private Move getBestMove() {
        List<Move> moves = board.getAllPossibleMoves(2);

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Move move : moves) {
            Board copy = new Board(board);
            copy.movePawn(move);
            int score = minimax(copy, 1, false, 1); // now Player 1's turn
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private void makeMiniMaxAIMove() {

        Move bestMove = getBestMove();

        board.movePawn(bestMove);

        view.updateButton(bestMove.fromRow, bestMove.fromCol, null);
        view.updateButton(bestMove.toRow, bestMove.toCol, board.getPawn(bestMove.toRow, bestMove.toCol));

        currentPlayer = 1;

        checkWinConditions();
        if (gameOver) {
            return;
        }

        view.showNextPlayer(currentPlayer);
    }


    private int minimax(Board board, int player, boolean isMax, int depth) {
        GameState state = board.evaluateGameState(player);

        int score = 10 - depth;
        if (state == GameState.WIN) {
            score = (player == 2) ? score : -score;
            return score;
        } else if (state == GameState.LOSS) {
            score = (player == 2) ? -score : score;
            return score;
        }

        List<Move> moves = board.getAllPossibleMoves(player);
        if (moves.isEmpty()) {
            score = (player == 2) ? -score : score;
            return score;
        }

        if (depth >= maxDepth) {
            return 0;
        }

        if (isMax) {
            int bestScore = Integer.MIN_VALUE;

            for (Move move : moves) {
                Board copy = new Board(board);
                copy.movePawn(move);

                score = minimax(copy, 3 - player, false, depth + 1);
                bestScore = Math.max(bestScore, score);
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (Move move : moves) {
                Board copy = new Board(board);
                copy.movePawn(move);
                score = minimax(copy, 3 - player, true, depth + 1);
                bestScore = Math.min(bestScore, score);
            }
            return bestScore;
        }
    }

    private void makeFirstMoveFromList() {
        List<Move> moves = board.getAllPossibleMoves(2);

        if (moves.isEmpty()) {
            view.showWinner(1); // Player 1 wins
            gameOver = true;
            return;
        }

        Move move = moves.getFirst();
        board.movePawn(move);

        view.updateButton(move.fromRow, move.fromCol, null);
        view.updateButton(move.toRow, move.toCol, board.getPawn(move.toRow, move.toCol));

        checkWinConditions();
        if (gameOver) {
            return;
        }

        currentPlayer = 1;
        view.showNextPlayer(currentPlayer);
    }

    private void makeRandomAIMove() {
        List<Move> moves = board.getAllPossibleMoves(2);

        Move move = moves.get(new Random().nextInt(moves.size()));
        board.movePawn(move);

        view.updateButton(move.fromRow, move.fromCol, null);
        view.updateButton(move.toRow, move.toCol, board.getPawn(move.toRow, move.toCol));

        currentPlayer = 1;

        checkWinConditions();
        if (gameOver) {
            return;
        }
        view.showNextPlayer(currentPlayer);
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

    private void checkWinConditions() {
        GameState state = board.evaluateGameState(currentPlayer);
        if (state == GameState.WIN) {
            view.showWinner(currentPlayer);
            gameOver = true;
        } else if (state == GameState.LOSS) {
            view.showWinner(3 - currentPlayer);
            gameOver = true;
        }
    }

    private void handlePawnMove(int row, int col) {
        if (isValidMove(selectedRow, selectedCol, row, col)) {
            Move move = new Move(selectedRow, selectedCol, row, col);
            board.movePawn(move);
            view.updateButton(selectedRow, selectedCol, null);
            view.updateButton(row, col, board.getPawn(row, col));
            System.out.println("Moved to (" + row + ", " + col + ")");

            currentPlayer = 3 - currentPlayer ;

            checkWinConditions();
            if (gameOver) {
                return;
            }
            view.showNextPlayer(currentPlayer);
            if (currentPlayer == 2 && player2IsRandomAI) {
                makeRandomAIMove();
            } else if (currentPlayer == 2 && player2IsMiniMaxAI) {
                makeMiniMaxAIMove();
            }

        } else {
            System.out.println("Cannot move to non-empty spot!");
        }

        view.clearHighlights();
        selectedRow = -1;
        selectedCol = -1;
    }

    private void handleClick(int row, int col) {

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
