package com.hexapawn.hexapawn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        int SIZE = 3;
        int ROWS = SIZE;
        int COLS = SIZE;
        int MAX_DEPTH = 6;
        Board board = new Board(ROWS, COLS);
        View view = new View(board);
        Controller controller = new Controller(board, view, MAX_DEPTH);

        Scene scene = new Scene(view.getRoot(), ROWS * 100, COLS * 100 + 100);
        stage.setScene(scene);
        stage.setTitle("Hexapawn");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
