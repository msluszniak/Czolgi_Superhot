package agh.cs.project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {
    private TextField sideLength;
    private int level;
    private Engine engine;
    private final HBox hBox;
    private final Scene scene;

    //klasa odpowiedzialna za sterowanie przebiegiem, a także tworzeniem panelu do ustalenia opcji gry

    public ControlPanel(HBox hBox, Scene scene) {
        this.hBox = hBox;
        this.scene = scene;
        this.getChildren().addAll(createSideLengthTextField(), changeLevelButton("Latwy", 0),
                changeLevelButton("Sredni", 1), changeLevelButton("Trudny", 2), startGame());
    }

    public TextField createSideLengthTextField() {
        sideLength = new TextField("Rozmiar mapy");
        return sideLength;
    }


    public Button changeLevelButton(String string, int level) {
        Button button = new Button(string);
        button.setOnAction(e -> this.level = level);
        return button;
    }

    public Button startGame() {
        Button button = new Button("Start gry!");
        button.setOnAction(e -> {
            System.out.println(sideLength.getText());
            engine = new Engine(Integer.parseInt(sideLength.getText()), 20, 20, level);
            BattleFieldVisualizer battleFieldVisualizer = new BattleFieldVisualizer(engine);
            hBox.getChildren().add(battleFieldVisualizer.battleFieldMap());
            KeyEventHandler keyEventHandler = new KeyEventHandler(battleFieldVisualizer, hBox);
            scene.setOnKeyPressed(keyEventHandler::onKeyPressedEvent);
        });
        return button;
    }

}
