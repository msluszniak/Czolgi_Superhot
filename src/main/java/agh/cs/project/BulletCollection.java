package agh.cs.project;

import java.util.HashMap;
import java.util.Map;

public class BulletCollection {
    private final Map<Vector2d, Bullet> bullets = new HashMap<>();

    public void addBullet(IMapElement element) {
        bullets.put(element.getPosition(), (Bullet) element);
    }

    public void removeBullet(IMapElement element) {
        Vector2d actualPosition = element.getPosition();
        bullets.remove(actualPosition);
    }

    public void removeBulletByPosition(Vector2d oldPosition) {
        bullets.remove(oldPosition);
    }

    public Map<Vector2d, Bullet> getBullets() {
        return bullets;
    }


}
