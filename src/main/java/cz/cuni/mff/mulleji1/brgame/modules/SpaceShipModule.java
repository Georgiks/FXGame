package cz.cuni.mff.mulleji1.brgame.modules;

import cz.cuni.mff.mulleji1.brgame.SpaceShip;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class SpaceShipModule {
    String name;
    Image icon;

    Group graphics;
    Circle backgroundCircle;

    public SpaceShipModule(String name, Image icon) {
        this.name = name;
        this.icon = icon;

        this.graphics = new Group();

        backgroundCircle = new Circle(24, Color.DARKCYAN);
        ImageView iw = new ImageView(icon);
        double iconSize = 30;
        iw.setFitWidth(iconSize);
        iw.setFitHeight(iconSize);
        iw.setX(- iconSize / 2);
        iw.setY(- iconSize / 2);

        this.graphics.getChildren().addAll(backgroundCircle, iw);
    }

    public String getName() {
        return name;
    }

    public abstract void update(SpaceShip owner, long deltaNanos);

    public Node getStatusGraphics() {
        return graphics;
    }
    public Image getIcon() {
        return icon;
    }
}
