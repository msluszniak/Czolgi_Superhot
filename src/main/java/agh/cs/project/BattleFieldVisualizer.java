package agh.cs.project;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BattleFieldVisualizer {
    private final Engine engine;

    public BattleFieldVisualizer(Engine engine) {
        this.engine = engine;
    }

    public StackPane battleFieldMap() {
        int x = engine.getBattleField().getUpperRight().x;
        int y = engine.getBattleField().getUpperRight().y;
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();
        for (int i = 0; i < x; i++) {
            HBox hBox = new HBox();
            for (int j = 0; j < y; j++) {
                Vector2d position = new Vector2d(j, i);
                IMapElement element = engine.getBattleField().getElementAt(position);
                if (element instanceof Tank && ((Tank) element).getId() == 0) {
                    int angle = ((Tank) element).getCurrentMovement().angleFromVector();
                    ImageView imageView = new ImageView("czolg_gracza.png");
                    imageView.setRotate(angle);
                    hBox.getChildren().add(imageView);
                } else if (element instanceof Tank && ((Tank) element).getId() == 1) {
                    int angle = ((Tank) element).getCurrentMovement().angleFromVector();
                    ImageView imageView = new ImageView("czolg_wrog.png");
                    imageView.setRotate(angle);
                    hBox.getChildren().add(imageView);
                } else if (element instanceof Obstacle)
                    hBox.getChildren().add(new ImageView("przeszkoda.png"));
                else if (element instanceof Bullet) {
                    int angle = ((Bullet) element).getMoveDirection().angleFromVector();
                    ImageView imageView = new ImageView("pocisk.png");
                    imageView.setRotate(angle);
                    hBox.getChildren().add(imageView);
                } else
                    hBox.getChildren().add(new ImageView("plytka.png"));
            }
            vBox.getChildren().add(hBox);
        }

        stackPane.getChildren().add(vBox);
        return stackPane;
    }

    public Engine getEngine() {
        return engine;
    }
}
