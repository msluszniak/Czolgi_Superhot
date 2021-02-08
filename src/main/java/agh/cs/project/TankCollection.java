package agh.cs.project;

import java.util.HashMap;
import java.util.Map;

public class TankCollection {
    private final Map<Vector2d, Tank> tanks = new HashMap<>();

    public void addTank(IMapElement element) {
        tanks.put(element.getPosition(), (Tank) element);
    }

    public void removeTank(IMapElement element) {
        Vector2d actualPosition = element.getPosition();
        tanks.remove(actualPosition);
    }

    public void removeTankByPosition(Vector2d oldPosition) {
        tanks.remove(oldPosition);
    }

    public Map<Vector2d, Tank> getTanks() {
        return tanks;
    }

}
