package cz.cuni.mff.mulleji1.brgame;

import cz.cuni.mff.mulleji1.brgame.modules.StealthModule;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.hexworks.mixite.core.api.*;

import java.util.HashSet;
import java.util.Set;

public class GameLoop extends AnimationTimer {
    Set<SpaceObject> gameObjects = new HashSet<>();
    /**
     * Main group used to render things
     */
    Group group;
    Player thisPlayer;
    HUDManager hudManager;
    HexagonalGrid<GameSectorData> sectors;

    private long previousTime = Long.MIN_VALUE;

    private static final double INIT_WIDTH = 1600;
    private static final double INIT_HEIGHT = 900;

    public GameLoop(Group g) {
        Group gameGroup = new Group();
        this.group = gameGroup;
        hudManager = new HUDManager();

        // create map
        HexagonalGridBuilder<GameSectorData> hexBuilder = new HexagonalGridBuilder<GameSectorData>()
                .setGridWidth(7)
                .setGridHeight(7)
                .setGridLayout(HexagonalGridLayout.HEXAGONAL)
                .setOrientation(HexagonOrientation.FLAT_TOP)
                .setRadius(64);
        sectors = hexBuilder.build();
        // fill every sector of map with game content
        BorderSectorContentFactory fact = new BorderSectorContentFactory(this);
        sectors.getHexagons().forEach(hex -> hex.setSatelliteData(new GameSectorData(fact, hex)));

        // add graphics of sectors
        SectorBoundariesGroup sectorsGroup = new SectorBoundariesGroup(sectors);
        gameGroup.getChildren().add(sectorsGroup);

        // draw background
        Canvas canvasUniverse = new Canvas(INIT_WIDTH, INIT_HEIGHT);
        Canvas canvasStars = new Canvas(INIT_WIDTH, INIT_HEIGHT);
        drawUniversebackground(canvasUniverse);
        // TODO: stars should slightly move when moving camera, as if they were in large but finite distance
        drawStarsBackground(canvasStars);

        // create HUD
        Node hud = hudManager.getForObject(null);
        hud.setTranslateX((INIT_WIDTH - hudManager.getWidth()) / 2);
        hud.setTranslateY(INIT_HEIGHT - hudManager.getHeight());

        // add all graphics to main group
        g.getChildren().addAll(canvasUniverse, canvasStars, gameGroup, hud);

        // unselect when clicked nowhere
        canvasStars.setOnMouseClicked(event -> selectObject(null));

        // create player ship
        thisPlayer = new Player("Georgik");

        PlayerSpaceShip playerSpaceShip = new PlayerSpaceShip(600,600, 1000, thisPlayer, 10);
        playerSpaceShip.modules.add(new StealthModule());
        playerSpaceShip.modules.add(new StealthModule());
        addObject(playerSpaceShip);
    }

    /**
     * Draw stars to a canvas
     * @param c canvas to be drawn on
     */
    void drawStarsBackground(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        Image img = new Image(GameLoop.class.getResourceAsStream("stars_background.png"));

        for (int x = 0; x < c.getWidth(); x += img.getWidth()) {
            for (int y = 0; y < c.getHeight(); y += img.getHeight()) {
                gc.drawImage(img, x, y);
            }
        }
    }

    /**
     * Draw static background
     * @param c canvas to be drawn on
     */
    void drawUniversebackground(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        Image img = new Image(GameLoop.class.getResourceAsStream("universe_background.png"));

        double imgX = img.getWidth() / 1.0;
        double imgY = img.getHeight() / 1.0;
        for (int x = -200; x < c.getWidth(); x += imgX) {
            for (int y = -200; y < c.getHeight(); y += imgY) {
                gc.drawImage(img, x, y, imgX, imgY);
            }
        }
    }

    /**
     * Main loop
     * @param now current tick
     */
    @Override
    public void handle(long now) {
        long delta = now - previousTime;
        if (previousTime == Long.MIN_VALUE) {
            // on first tick, delta is incorrect, skip
            previousTime = now;
            return;
        }
        previousTime = now;

        // input
        //  handled via listeners

        // update
        // TODO: apply some destruction effect
        if (selected != null && selected.getHP() <= 0) selectObject(null);
        gameObjects.removeIf(spaceObject -> spaceObject.getHP() <= 0);

        for (SpaceObject o : gameObjects) {
            o.update(this, delta);

            // DEBUG: try decreasing ship health
            if (o instanceof PlayerSpaceShip)
                o.applyDamage(o.getHP() < o.getMaxHP() / 4 ? 0 : 2, DamageType.KINETIC);
        }

        // render
        //  objects are drawn as shapes, not in canvas
    }

    /**
     * Add object to the game
     * All objects should be added using this method
     * @param obj object to be added to the game
     */
    public void addObject(SpaceObject obj) {
        gameObjects.add(obj);
        Node graphics = obj.getGraphics();
        graphics.setOnMouseClicked(event -> onObjectClicked(obj, event));
        if (obj instanceof MovableSpaceObject) {
            MovableSpaceObject mso = (MovableSpaceObject)obj;
            group.getChildren().add(mso.getWaypointsGraphics());
        }
        group.getChildren().add(graphics);
    }

    /**
     * Currently selected object
     */
    SpaceObject selected;

    /**
     * Handle object is clicked
     * @param object object that was clicked
     * @param event mouse event
     */
    public void onObjectClicked(SpaceObject object, MouseEvent event) {
        MouseButton button = event.getButton();
        if (button == MouseButton.PRIMARY) {
            selectObject(object);
        } else if (button == MouseButton.SECONDARY) {
            if (selected instanceof PlayerSpaceShip && object instanceof StaticWaypointObject) {
                tryMoveShip((PlayerSpaceShip)selected, object);
            }
        }
    }

    /**
     * Select game object, showing HUD if available
     * @param object object to be selected
     */
    protected void selectObject(SpaceObject object) {
        if (selected != null) {
            selected.unselect();
        }
        if (object != null) {
            object.select();
        }
        selected = object;

        hudManager.getForObject(object);
    }

    /**
     * Process command to move ship to an object
     * @param ship ship to be moved
     * @param target target object
     */
    protected void tryMoveShip(PlayerSpaceShip ship, SpaceObject target) {
        if (ship.getPlayer() == thisPlayer) {
            ship.addWaypoint(new WaypointInfo(target.getPosX(), target.getPosY()));
        }
    }
}
