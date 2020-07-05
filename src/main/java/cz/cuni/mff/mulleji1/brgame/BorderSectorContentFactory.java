package cz.cuni.mff.mulleji1.brgame;

import org.hexworks.mixite.core.api.Hexagon;
import org.hexworks.mixite.core.api.Rectangle;

/**
 * Factory to create content of sectors located at the edge of map
 */
public class BorderSectorContentFactory extends SectorContentFactory{
    static final int StaticWaypoints = 5;


    public BorderSectorContentFactory(GameLoop gameLoop) {
        super(gameLoop);
    }

    @Override
    public void generateContent(Hexagon hex, GameSectorData data) {
        Rectangle bb = hex.getInternalBoundingBox();

        // create asteroids inside sector
        for (int i = 0; i < StaticWaypoints; i++) {
            double x = hex.getCenterX() + rnd.nextFloat() * bb.getWidth() - bb.getWidth() / 2;
            double y = hex.getCenterY() + rnd.nextFloat() * bb.getHeight() - bb.getHeight() / 2;

            StaticWaypointObject wo = new StaticWaypointObject(x, y);
            mainLoop.addObject(wo);
            data.staticSectorContent.add(wo);
        }
        // create NPC ships
        for (int i = 0; i < 4; i++) {
            double x = hex.getCenterX() + rnd.nextFloat() * bb.getWidth() - bb.getWidth() / 2;
            double y = hex.getCenterY() + rnd.nextFloat() * bb.getHeight() - bb.getHeight() / 2;

            GuardianSpaceShip gss = new GuardianSpaceShip(x, y);
            mainLoop.addObject(gss);
        }
    }
}
