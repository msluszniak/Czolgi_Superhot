package agh.cs.project;

import java.util.HashMap;

public class Tank implements IMapElement {
    private Vector2d position;
    private Vector2d currentMovement = new Vector2d(0, -1);
    private int numberOfLives;
    private final HashMap<Integer, Integer> PowerUps = new HashMap<>();
    private final int id;
    private final agh.cs.project.BattleField BattleField;

    public Tank(Vector2d position, int numberOfLive, int id,
                BattleField battleField) {
        this.position = position;
        this.numberOfLives = numberOfLive;
        this.id = id;
        this.BattleField = battleField;
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isWracked() {
        return this.numberOfLives <= 0;
    }

    public void tankPositionChanged(Vector2d oldPosition) {
        BattleField.tankPositionChanged(oldPosition, this);
    }

    public void numberOfLivesChanged() {
        BattleField.numberOfLivesChangedTank(this);
    }

    public BattleField getBattleField() {
        return this.BattleField;
    }

    public int getId() {
        return this.id;
    }

    public void changeNumberOfLives(int change) {
        this.numberOfLives -= change;
        this.numberOfLivesChanged();
    }

    //wykonanie ruchu jeśli jest możliwy
    public boolean move(Vector2d movement) {
        Vector2d lowerLeft = this.BattleField.getLowerLeft();
        Vector2d upperRight = this.BattleField.getUpperRight();
        if (!this.getPosition().add(movement).isInArea(lowerLeft, upperRight)) {
            return false;
        }
        if (this.BattleField.isOccupied(this.getPosition().add(movement))) {
            return false;
        } else {
            Vector2d oldPosition = this.getPosition();
            position = position.add(movement);
            this.tankPositionChanged(oldPosition);
            return true;
        }
    }

    //wykonanie strzału jeśli jest możliwy
    public boolean shoot(Vector2d movement) {
        Vector2d lowerLeft = this.BattleField.getLowerLeft();
        Vector2d upperRight = this.BattleField.getUpperRight();
        if (!this.getPosition().add(movement).isInArea(lowerLeft, upperRight)) {
            return false;
        } else {
            IMapElement bullet;
            if (this.id == 1) {
                bullet = new Bullet(this.getPosition().add(movement), movement, this.BattleField);
            } else {
                bullet = new Bullet(this.getPosition(), movement, this.BattleField);
            }
            this.getBattleField().addElement(bullet);
            return true;
        }
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setCurrentMovement(Vector2d newMovement) {
        this.currentMovement = newMovement;
    }

    public Vector2d getCurrentMovement() {
        return this.currentMovement;
    }


}
