package cz.cuni.mff.mulleji1.brgame;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerSpaceShip extends SpaceShip {
    protected Player player;
    Group graphics;
    Rectangle shipRectangle;

    public PlayerSpaceShip(double x, double y, int maxHP, Player player, double speed) {
        super(x, y, maxHP, speed);
        this.player = player;
    }

    @Override
    protected void initGraphics() {
        graphics = new Group();
        shipRectangle = new Rectangle(-20,-10, 40, 20);
        shipRectangle.setFill(Color.BLUE);
        graphics.getChildren().add(shipRectangle);
        // retrieve bar and rescale it for 40x5
        Node hpBar = getHPBar();
        hpBar.setScaleX(4);
        hpBar.setScaleY(0.5);
        hpBar.setTranslateY(-15);
        graphics.getChildren().add(hpBar);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Node getGraphics() {
        return graphics;
    }

    @Override
    public void select() {
        shipRectangle.setFill(Color.CYAN);
    }

    @Override
    public void unselect() {
        shipRectangle.setFill(Color.BLUE);
    }

    @Override
    public String getDisplayName() {
        return "Battleship";
    }
}
