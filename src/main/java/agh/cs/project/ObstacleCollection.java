package agh.cs.project;

import java.util.HashMap;
import java.util.Map;

public class ObstacleCollection {
    private final Map<Vector2d, Obstacle> obstacles = new HashMap<>();

    public void addObstacle(IMapElement element) {
        obstacles.put(element.getPosition(), (Obstacle) element);
    }

    public void removeObstacle(IMapElement element) {
        Vector2d actualPosition = element.getPosition();
        obstacles.remove(actualPosition);
    }

    public Map<Vector2d, Obstacle> getObstacles() {
        return obstacles;
    }


}
