package agh.cs.project;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class KeyEventHandler {
    private final BattleFieldVisualizer battleFieldVisualizer;
    private final Engine engine;
    private final HBox hBox;

    public KeyEventHandler(BattleFieldVisualizer battleFieldVisualizer, HBox hBox) {
        this.engine = battleFieldVisualizer.getEngine();
        this.battleFieldVisualizer = battleFieldVisualizer;
        this.hBox = hBox;
    }

    public void onKeyPressedEvent(KeyEvent e) {
        switch (e.getCode().toString()) {
            case "A" -> engine.getBattleField().getPlayer().move(new Vector2d(-1, 0));
            case "W" -> engine.getBattleField().getPlayer().move(new Vector2d(0, -1));
            case "D" -> engine.getBattleField().getPlayer().move(new Vector2d(1, 0));
            case "S" -> engine.getBattleField().getPlayer().move(new Vector2d(0, 1));
            case "Q" -> engine.getBattleField().getPlayer().setCurrentMovement(engine.getBattleField().getPlayer().getCurrentMovement().nextVector());
            case "E" -> engine.getBattleField().getPlayer().setCurrentMovement(engine.getBattleField().getPlayer().getCurrentMovement().previousVector());
            case "X" -> engine.getBattleField().getPlayer().shoot(engine.getBattleField().getPlayer().getCurrentMovement());
        }
        if (isImportantChange(e)) {
            engine.fullStep();
        }
        hBox.getChildren().clear();
        hBox.getChildren().add(battleFieldVisualizer.battleFieldMap());

    }

    public boolean isImportantChange(KeyEvent e) {
        return switch (e.getCode().toString()) {
            case "A", "W", "D", "S", "X" -> true;
            default -> false;
        };
    }
}
