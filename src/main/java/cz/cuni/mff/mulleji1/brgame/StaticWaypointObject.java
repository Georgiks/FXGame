package cz.cuni.mff.mulleji1.brgame;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Static objects (asteroids) used as waypoints targets
 */
public class StaticWaypointObject extends SpaceObject {
    Circle graphics;

    public StaticWaypointObject(double x, double y) {
        super(x, y, Integer.MAX_VALUE);
    }

    @Override
    public void update(GameLoop gameLoop, long deltaNanos) {
        // nothing
    }

    @Override
    public Node getGraphics() {
        return graphics;
    }

    @Override
    protected void initGraphics() {
        // waypoints doesnt show HP, graphics is just simple as one shape
        graphics = new Circle(10, Color.PURPLE);
    }

    @Override
    public String getDisplayName() {
        return "Asteroid";
    }
}
