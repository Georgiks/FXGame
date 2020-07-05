package cz.cuni.mff.mulleji1.brgame;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.hexworks.mixite.core.api.Hexagon;
import org.hexworks.mixite.core.vendor.Maybe;

import java.util.Comparator;
import java.util.Optional;
import java.util.Random;

/**
 * Low damage high HP NPC ship
 */
public class GuardianSpaceShip extends UnplayableSpaceShip {
    Group graphics;
    Random rnd = new Random();

    public GuardianSpaceShip(double x, double y) {
        super(x, y, 600, 6);
    }

    @Override
    public void update(GameLoop gameLoop, long deltaNanos) {
        // only check for players with low probability
        if (rnd.nextFloat() < 0.003) {
            Maybe<Hexagon<GameSectorData>> mh = gameLoop.sectors.getByPixelCoordinate(getPosX(), getPosY());
            if (mh.isPresent()) {
                Hexagon<GameSectorData> h = mh.get();
                // search for nearest player ship
                Optional<SpaceObject> first = gameLoop.gameObjects.stream()
                        .filter(spaceObject -> spaceObject instanceof PlayerSpaceShip)
                        .filter(spaceObject -> gameLoop.sectors.getByPixelCoordinate(spaceObject.getPosX(), spaceObject.getPosY()).equals(mh))
                        .min(Comparator.comparingDouble(o -> Utils.getDistanceSquared(getPosX(), getPosY(), o.getPosX(), o.getPosY())));
                // if there is one, move to the static object nearest to the found ship
                if (first.isPresent()) {
                    SpaceShip ship = (PlayerSpaceShip)first.get();
                    h.getSatelliteData().ifPresent(
                            d -> d.staticSectorContent.stream()
                                    .min(Comparator.comparingDouble(o -> Utils.getDistanceSquared(o.getPosX(), o.getPosY(), ship.getPosX(), ship.getPosY())))
                                    .ifPresent(so -> addWaypoint(new WaypointInfo(so.getPosX(), so.getPosY()))));
                }
            }
        }

        super.update(gameLoop, deltaNanos);
    }

    @Override
    public Node getGraphics() {
        return graphics;
    }

    @Override
    protected void initGraphics() {
        graphics = new Group();
        Rectangle r = new Rectangle(-5,-5, 10, 10);
        r.setFill(Color.PINK);
        // customize HP bar
        Node hpBar = getHPBar();
        hpBar.setScaleX(2);
        hpBar.setScaleY(0.3);
        hpBar.setTranslateY(-8);
        graphics.getChildren().add(r);
        graphics.getChildren().add(hpBar);
    }

    @Override
    public String getDisplayName() {
        return "Guardian";
    }
}
