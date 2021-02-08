package agh.cs.project;

public class Bullet implements IMapElement {
    private Vector2d position;
    private final Vector2d moveDirection;
    private final agh.cs.project.BattleField battlefield;

    public Bullet(Vector2d position, Vector2d moveDirection, BattleField battleField) {
        this.position = position;
        this.moveDirection = moveDirection;
        this.battlefield = battleField;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void bulletPositionChanged(Vector2d oldPosition) {
        battlefield.bulletPositionChanged(oldPosition, this);
    }

    public void move() {
        Vector2d lowerLeft = this.battlefield.getLowerLeft();
        Vector2d upperRight = this.battlefield.getUpperRight();
        if (this.getPosition().add(moveDirection).isInArea(lowerLeft, upperRight)) {
            Vector2d oldPosition = position;
            position = position.add(moveDirection);
            this.bulletPositionChanged(oldPosition);
        } else {
            battlefield.removeElement(this);
        }
    }

    public Vector2d getMoveDirection() {
        return moveDirection;
    }

    public Vector2d nextPosition(){
        return position.add(moveDirection);
    }
}
