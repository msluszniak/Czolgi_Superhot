package agh.cs.project;

import java.util.Random;
import java.util.Set;

public class Engine {
    Random random = new Random();
    private final BattleField battleField;
    private int score;
    private boolean end;
    private final int xObstacle;
    private final int xTank;
    private int counterObstacle = 0;
    private int counterTank = 0;
    private final int level;


    public Engine(int sideLength, int xTank, int xObstacle, int level) {
        this.battleField = new BattleField(sideLength);
        this.score = 0;
        this.end = false;
        this.xTank = xTank;
        this.xObstacle = xObstacle;
        this.level = level;
    }

    //obsługa akcji wrogów, 50% ruch w strone gracza, 50% strzał w stronę gracza
    public void enemiesShootOrMove() {
        for (Tank enemy : this.battleField.getTankIterable()) {
            if (enemy.getId() == 1) {
                double p = random.nextDouble();
                if (p < 0.5) {
                    Vector2d direction = enemy.getPosition().moveTowardsEnemy(battleField.getPlayer().getPosition());
                    enemy.setCurrentMovement(direction);
                    enemy.move(direction);
                } else {
                    Vector2d direction = enemy.getPosition().chooseShootDirection(battleField.getPlayer().getPosition());
                    enemy.setCurrentMovement(direction);
                    enemy.shoot(direction);
                }
            }
        }
    }

    //przesuwa pociski, w razie zderzeń pocisków usuwa je z mapy
    public void moveBullets() {
        battleField.headOnCollisions();
        for (Bullet bullet : Set.copyOf(battleField.getBulletCollection().getBullets().values())) {
            if (battleField.isBulletAtPosition(bullet.getPosition().add(bullet.getMoveDirection()))) {
                battleField.removeElement(battleField.getBulletAtPosition(bullet.nextPosition()));
                battleField.removeElement(bullet);
            } else
                bullet.move();
        }
    }

    public void spawnObstacle(double probability) {
        double p = random.nextDouble();
        if (counterObstacle >= xObstacle) {
            p = 0;
            counterObstacle = 0;
        }
        if (p <= probability) {
            boolean flag = true;
            while (flag) {
                int xCoordinate = random.nextInt(battleField.getUpperRight().x);
                int yCoordinate = random.nextInt(battleField.getUpperRight().y);
                Vector2d position = new Vector2d(xCoordinate, yCoordinate);
                if (!battleField.isOccupiedWithBullets(position)) {
                    battleField.addElement(new Obstacle(position, battleField));
                    flag = false;
                }
            }
        } else counterObstacle++;
    }

    public void spawnEnemy(double probability) {
        double p = random.nextDouble();
        if (counterTank > xTank) {
            p = 0;
            counterTank = 0;
        }
        if (battleField.getTankCollection().getTanks().size() <= 1) p = 0;
        if (p <= probability) {
            boolean flag = true;
            while (flag) {
                int xCoordinate = random.nextInt(battleField.getUpperRight().x);
                int yCoordinate = random.nextInt(battleField.getUpperRight().y);
                Vector2d position = new Vector2d(xCoordinate, yCoordinate);
                if (!battleField.isOccupiedWithBullets(position) &&
                        position.checkCorrectDistance(battleField.getPlayer().getPosition())) {
                    battleField.addElement(new Tank(position, 1, 1, battleField));
                    flag = false;
                }
            }
        } else counterTank++;
    }

    //metoda zadaje obrażenia obiektom, które weszły w pole rażenia pewnego pocisku
    public void makeDamage() {
        for (Bullet bullet : Set.copyOf(battleField.getBulletCollection().getBullets().values())) {
            if (battleField.isOccupied(bullet.getPosition())) {
                if (battleField.getTankCollection().getTanks().containsKey(bullet.getPosition())) {
                    battleField.getTankCollection().getTanks().get(bullet.getPosition()).changeNumberOfLives(1);
                } else {
                    battleField.getObstacleCollection().getObstacles().get(bullet.getPosition()).changeNumberOfLives(1);
                }
                battleField.removeElement(bullet);
            }
        }
    }

    public void removeDead() {
        for (Tank tank : battleField.getTankIterable()) {
            if (tank.isWracked()) {
                int id = tank.getId();
                battleField.removeElement(tank);
                if (id == 1) score += 1;
                if (id == 0) end = true;
            }
        }
        for (Obstacle obstacle : Set.copyOf(battleField.getObstacleCollection().getObstacles().values())) {
            if (obstacle.isDestroyed()) {
                battleField.removeElement(obstacle);
            }
        }
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public boolean fullStep() {
        this.removeDead();
        if (this.end) return false;
        else {
            this.moveBullets();
            this.enemiesShootOrMove();
            this.spawnObstacle(0.1);
            if (level == 0) {
                this.spawnEnemy(0.03);
            } else if (level == 1) {
                this.spawnEnemy(0.06);
            } else this.spawnEnemy(0.1);
            this.makeDamage();
            System.out.println("Twoj wynik to " + score + ", a aktualna ilosc zyc to " +
                    battleField.getPlayer().getNumberOfLives() + ".");
            return true;
        }
    }

}
