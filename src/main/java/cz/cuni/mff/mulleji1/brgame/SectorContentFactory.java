package cz.cuni.mff.mulleji1.brgame;

import org.hexworks.mixite.core.api.Hexagon;

import java.util.Random;

/**
 * Abstract factory to create content of sectors
 */
public abstract class SectorContentFactory {
    GameLoop mainLoop;
    Random rnd = new Random();

    public SectorContentFactory(GameLoop gameLoop) {
        this.mainLoop = gameLoop;
    }

    public abstract void generateContent(Hexagon sector, GameSectorData sectorData);
}
