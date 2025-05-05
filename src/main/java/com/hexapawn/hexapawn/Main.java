package com.hexapawn.hexapawn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Board board = new Board(3, 3);
        View view = new View(board);
        Controller controller = new Controller(board, view);

        Scene scene = new Scene(view.getGridPane());
        stage.setScene(scene);
        stage.setTitle("Hexapawn");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
