import agh.cs.project.Engine;
import agh.cs.project.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TankTest {
    Engine engine = new Engine(20, 20, 20, 1);

    @Test
    public void ifPlayerMove(){
        Vector2d position = engine.getBattleField().getPlayer().getPosition();
        engine.getBattleField().getPlayer().move(new Vector2d(1, 0));
        Assertions.assertNotEquals(position, engine.getBattleField().getPlayer().getPosition());
        Assertions.assertFalse(engine.getBattleField().isOccupied(position));
    }

}
