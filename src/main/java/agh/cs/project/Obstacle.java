package agh.cs.project;

public class Obstacle implements IMapElement {
    private final Vector2d position;
    private int numberOfLives;
    private final BattleField battleField;

    public Obstacle(Vector2d initPos, BattleField battleField) {
        this.position = initPos;
        this.numberOfLives = 2;
        this.battleField = battleField;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void changeNumberOfLives(int change) {
        this.numberOfLives -= change;
        this.numberOfLivesChanged();
    }

    public boolean isDestroyed() {
        return numberOfLives <= 0;
    }

    public void numberOfLivesChanged() {
        battleField.numberOfLivesChangedObstacle(this);
    }


}