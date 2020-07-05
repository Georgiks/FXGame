package cz.cuni.mff.mulleji1.brgame;

import org.hexworks.mixite.core.api.Hexagon;
import org.hexworks.mixite.core.api.defaults.DefaultSatelliteData;

import java.util.ArrayList;
import java.util.List;

/**
 * Data associated with sector
 * Can be used by other objects when some sector-wise behaviour is needed
 */
public class GameSectorData extends DefaultSatelliteData {
    List<SpaceObject> staticSectorContent = new ArrayList<>();

    public GameSectorData(SectorContentFactory factory, Hexagon hexagon) {
        factory.generateContent(hexagon, this);
    }

    /**
     * @return List of all static objects in given sector
     */
    public List<SpaceObject> getSectorContents() {
        return staticSectorContent;
    }
}
