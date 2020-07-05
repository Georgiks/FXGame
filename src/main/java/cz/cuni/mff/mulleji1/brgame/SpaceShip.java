package cz.cuni.mff.mulleji1.brgame;

import cz.cuni.mff.mulleji1.brgame.modules.SpaceShipModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Ship that can move and can contain modules
 */
public abstract class SpaceShip extends MovableSpaceObject {
    List<SpaceShipModule> modules = new ArrayList<>();

    public SpaceShip(double x, double y, int maxHP, double speed) {
        super(x, y, maxHP, speed);
    }

    @Override
    public void update(GameLoop gameLoop, long deltaNanos) {
        for (SpaceShipModule module : modules) {
            module.update(this, deltaNanos);
        }
        super.update(gameLoop, deltaNanos);
    }
}
