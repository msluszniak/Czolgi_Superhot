package agh.cs.project;

import java.util.Collection;
import java.util.Set;

public class BattleField {
    private final TankCollection tankCollection = new TankCollection();
    private final BulletCollection bulletCollection = new BulletCollection();
    private final ObstacleCollection obstacleCollection = new ObstacleCollection();
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Tank player;

    public BattleField(int sideLength) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(sideLength, sideLength);
        this.player = new Tank(new Vector2d(sideLength / 2, sideLength / 2), 3, 0, this);
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public void numberOfLivesChangedTank(Tank tank) {
        this.tankCollection.removeTank(tank);
        this.tankCollection.addTank(tank);
    }

    public void numberOfLivesChangedObstacle(Obstacle obstacle) {
        this.obstacleCollection.removeObstacle(obstacle);
        this.obstacleCollection.addObstacle(obstacle);
    }

    public void tankPositionChanged(Vector2d oldPosition, Tank tank) {
        this.tankCollection.removeTankByPosition(oldPosition);
        tankCollection.addTank(tank);
    }

    public void bulletPositionChanged(Vector2d oldPosition, Bullet bullet) {
        this.bulletCollection.removeBulletByPosition(oldPosition);
        bulletCollection.addBullet(bullet);
    }

    public void addElement(IMapElement element) {
        if (element instanceof Obstacle) {
            obstacleCollection.addObstacle(element);
        } else if (element instanceof Tank) {
            tankCollection.addTank(element);
        } else {
            bulletCollection.addBullet(element);
        }
    }

    public void removeElement(IMapElement element) {
        if (element instanceof Obstacle) {
            obstacleCollection.removeObstacle(element);
        } else if (element instanceof Tank) {
            tankCollection.removeTank(element);
        } else {
            bulletCollection.removeBullet(element);
        }
    }


    public boolean isOccupied(Vector2d position) {
        if (tankCollection.getTanks().containsKey(position)) {
            return true;
        } else return obstacleCollection.getObstacles().containsKey(position);
    }

    public boolean isOccupiedWithBullets(Vector2d position) {
        if (tankCollection.getTanks().containsKey(position)) {
            return true;
        } else if (obstacleCollection.getObstacles().containsKey(position)) {
            return true;
        } else return bulletCollection.getBullets().containsKey(position);
    }

    public TankCollection getTankCollection() {
        return tankCollection;
    }

    public BulletCollection getBulletCollection() {
        return bulletCollection;
    }

    public ObstacleCollection getObstacleCollection() {
        return obstacleCollection;
    }

    public Collection<Tank> getTankIterable() {
        return Set.copyOf((this.getTankCollection().getTanks().values()));
    }


    public Tank getPlayer() {
        return player;
    }

    public void headOnCollisions() {
        for (Bullet bullet : Set.copyOf(bulletCollection.getBullets().values())) {
            if (bulletCollection.getBullets().containsKey(bullet.getPosition().add(bullet.getMoveDirection())) &&
                    bulletCollection.getBullets().get(bullet.getPosition().add(bullet.getMoveDirection())).getPosition().add(bulletCollection.getBullets().get(bullet.getPosition().add(bullet.getMoveDirection())).getMoveDirection()).equals(bullet.getPosition())) {
                this.removeElement(bulletCollection.getBullets().get(bullet.getPosition().add(bullet.getMoveDirection())));
                this.removeElement(bullet);
            }
        }
    }

    public IMapElement getElementAt(Vector2d position) {
        if (this.bulletCollection.getBullets().containsKey(position)) {
            return this.bulletCollection.getBullets().get(position);
        } else if (this.obstacleCollection.getObstacles().containsKey(position)) {
            return this.obstacleCollection.getObstacles().get(position);
        } else if (this.tankCollection.getTanks().containsKey(position)) {
            return this.tankCollection.getTanks().get(position);
        } else return null;
    }

    public Bullet getBulletAtPosition(Vector2d position){
        return bulletCollection.getBullets().get(position);
    }

    public boolean isBulletAtPosition(Vector2d position){
        return bulletCollection.getBullets().containsKey(position);
    }


}
