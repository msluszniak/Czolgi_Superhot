package agh.cs.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        HBox hBox = new HBox();
        Scene scene = new Scene(hBox);
        hBox.getChildren().add(new ControlPanel(hBox, scene));
        stage.setScene(scene);
        stage.setTitle("Czolgi Superhot");
        stage.setMaximized(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
