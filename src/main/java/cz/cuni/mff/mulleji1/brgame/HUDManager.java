package cz.cuni.mff.mulleji1.brgame;

import cz.cuni.mff.mulleji1.brgame.modules.SpaceShipModule;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Helper class used to retrieve graphics for HUD
 */
public class HUDManager {
    private Rectangle background;
    private Text description;

    private Group generalGroup;
    private Group objectSpecificGroup;

    public HUDManager() {
        this.background = new Rectangle(200,50, Color.LIGHTSLATEGRAY);
        this.description = new Text("Placeholder");
        this.description.setFont(new Font(20));
        this.background.setTranslateY(100);
        this.description.setTranslateY(125);
        this.description.setTranslateX(5);


        this.objectSpecificGroup = new Group();
        this.generalGroup = new Group(background, description, objectSpecificGroup);
    }

    /**
     * Reset HUD group to contain information about given space object
     * @param object space object we want info about
     * @return the actual graphics (same group is reused, no need to remove/add group to render)
     */
    public Group getForObject(SpaceObject object) {
        // no object => hide HUD
        if (object == null) {
            generalGroup.setVisible(false);
            return generalGroup;
        }

        generalGroup.setVisible(true);
        description.setText(object.getDisplayName());
        objectSpecificGroup.getChildren().clear();

        // draw modules
        if (object instanceof SpaceShip) {
            SpaceShip ship = (SpaceShip)object;

            int offsetX = 24;
            for (SpaceShipModule module : ship.modules) {
                Node g = module.getStatusGraphics();
                g.setTranslateX(offsetX);
                g.setTranslateY(50);
                objectSpecificGroup.getChildren().add(g);
                offsetX += 50;
            }
        }
        return generalGroup;
    }

    public double getWidth() {
        return 200;
    }

    public double getHeight() {
        return 150;
    }
}
