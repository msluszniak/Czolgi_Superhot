package agh.cs.project;

import java.util.Random;

public class Vector2d {
    public final int x;
    public final int y;
    public final Random random = new Random();

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d((this.x + other.x), (this.y + other.y));
    }

    public boolean isInArea(Vector2d lowerLeft, Vector2d upperRight) {
        return lowerLeft.x <= this.x && upperRight.x > this.x && lowerLeft.y <= this.y && upperRight.y > this.y;
    }


    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    //AI wroga do poruszania, przybliża się wzdłuż jednej ze składowych
    public Vector2d moveTowardsEnemy(Vector2d enemyPosition) {
        Vector2d vectorBetweenRivals = enemyPosition.subtract(this);
        int variant = random.nextInt(2);
        if ((variant == 0 && vectorBetweenRivals.x != 0) || vectorBetweenRivals.y == 0) {
            if (vectorBetweenRivals.x > 0) return new Vector2d(1, 0);
            else return new Vector2d(-1, 0);
        } else {
            if (vectorBetweenRivals.y > 0) return new Vector2d(0, 1);
            else return new Vector2d(0, -1);
        }
    }

    //AI wroga do strzelania, wybiera odpowiedni obszar, w który powinien zostać wystrzelony pocisk
    //obliczany na podstawie kąta
    public Vector2d chooseShootDirection(Vector2d enemyPosition) {
        Vector2d vectorBetweenRivals = enemyPosition.subtract(this);
        if (vectorBetweenRivals.x == 0) {
            if (vectorBetweenRivals.y > 0) return new Vector2d(0, 1);
            else return new Vector2d(0, -1);
        } else {
            double alpha = Math.asin((vectorBetweenRivals.y / (Math.sqrt(Math.pow(vectorBetweenRivals.x, 2) +
                    Math.pow(vectorBetweenRivals.y, 2)))));
            boolean firstBoundary = alpha <= Math.PI / 8 && alpha > -Math.PI / 8;
            boolean secondBoundary = alpha <= -Math.PI / 8 && alpha > -3 * Math.PI / 8;
            boolean thirdBoundary = alpha <= -3 * Math.PI / 8;
            boolean fourthBoundary = alpha <= 3 * Math.PI && alpha > Math.PI;
            if (vectorBetweenRivals.x > 0) {
                if (firstBoundary) return new Vector2d(1, 0);
                else if (secondBoundary) return new Vector2d(1, -1);
                else if (thirdBoundary) return new Vector2d(0, -1);
                else if (fourthBoundary) return new Vector2d(1, 1);
                else return new Vector2d(0, 1);
            } else {
                if (firstBoundary) return new Vector2d(-1, 0);
                else if (secondBoundary) return new Vector2d(-1, -1);
                else if (thirdBoundary) return new Vector2d(0, -1);
                else if (fourthBoundary) return new Vector2d(-1, 1);
                else return new Vector2d(0, 1);
            }
        }
    }

    public boolean checkCorrectDistance(Vector2d playerPosition) {
        return Math.abs(this.x - playerPosition.x) > 2 || Math.abs(this.y - playerPosition.y) > 2;
    }

    public Vector2d previousVector() {
        if (this.equals(new Vector2d(0, 1))) return new Vector2d(-1, 1);
        else if (this.equals(new Vector2d(1, 1))) return new Vector2d(0, 1);
        else if (this.equals(new Vector2d(1, 0))) return new Vector2d(1, 1);
        else if (this.equals(new Vector2d(1, -1))) return new Vector2d(1, 0);
        else if (this.equals(new Vector2d(0, -1))) return new Vector2d(1, -1);
        else if (this.equals(new Vector2d(-1, -1))) return new Vector2d(0, -1);
        else if (this.equals(new Vector2d(-1, 0))) return new Vector2d(-1, -1);
        else if (this.equals(new Vector2d(-1, 1))) return new Vector2d(-1, 0);
        return null;
    }

    public Vector2d nextVector() {
        if (this.equals(new Vector2d(0, 1))) return new Vector2d(1, 1);
        else if (this.equals(new Vector2d(1, 1))) return new Vector2d(1, 0);
        else if (this.equals(new Vector2d(1, 0))) return new Vector2d(1, -1);
        else if (this.equals(new Vector2d(1, -1))) return new Vector2d(0, -1);
        else if (this.equals(new Vector2d(0, -1))) return new Vector2d(-1, -1);
        else if (this.equals(new Vector2d(-1, -1))) return new Vector2d(-1, 0);
        else if (this.equals(new Vector2d(-1, 0))) return new Vector2d(-1, 1);
        else if (this.equals(new Vector2d(-1, 1))) return new Vector2d(0, 1);
        else return null;
    }

    public int angleFromVector() {
        if (this.equals(new Vector2d(0, -1))) return 0;
        else if (this.equals(new Vector2d(1, -1))) return 45;
        else if (this.equals(new Vector2d(1, 0))) return 90;
        else if (this.equals(new Vector2d(1, 1))) return 135;
        else if (this.equals(new Vector2d(0, 1))) return 180;
        else if (this.equals(new Vector2d(-1, 1))) return 225;
        else if (this.equals(new Vector2d(-1, 0))) return 270;
        else if (this.equals(new Vector2d(-1, -1))) return 315;
        else return 0;
    }
}
