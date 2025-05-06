package com.hexapawn.hexapawn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Board board = new Board(3, 3);
        View view = new View(board);
        Controller controller = new Controller(board, view);

        VBox root = new VBox();
        root.getChildren().addAll(view.getStatusLabel(), view.getGridPane());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hexapawn");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
