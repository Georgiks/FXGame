package cz.cuni.mff.mulleji1.brgame.modules;

import cz.cuni.mff.mulleji1.brgame.SpaceShip;
import cz.cuni.mff.mulleji1.brgame.Utils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class ActivatableSpaceShipModule extends SpaceShipModule {
    double activeCountdown;
    double cooldown;

    double currentActiveCountdown = 0;
    double currentCooldown = 0;
    boolean isActive = false;

    Text timersText;

    public ActivatableSpaceShipModule(String name, Image icon) {
        super(name, icon);

        timersText = new Text();
        timersText.setFill(Color.WHITE);
        timersText.setVisible(false);
        timersText.setY(40);
        timersText.setX(-10);
        timersText.setTextAlignment(TextAlignment.CENTER);
        timersText.setFont(new Font(16));
        graphics.getChildren().add(timersText);

        graphics.setOnMouseClicked(event -> activate());
    }

    public void activate() {
        if (isReadyToActivate()) {
            currentActiveCountdown = activeCountdown;
            isActive = true;

            activateInternal();
        }
    }
    abstract void activateInternal();

    public boolean isReadyToActivate() {
        return !isActive && currentCooldown == 0;
    }

    @Override
    public void update(SpaceShip owner, long deltaNanos) {
        double seconds = Utils.nanoToSeconds(deltaNanos);
        if (isActive) {
            if (currentActiveCountdown <= seconds) {
                isActive = false;
                seconds -= currentActiveCountdown;
                currentActiveCountdown = 0;
                currentCooldown = cooldown;
            } else {
                currentActiveCountdown -= seconds;
                seconds = 0;
            }
        }
        if (!isActive && currentCooldown > 0) {
            currentCooldown = Math.max(0, currentCooldown - seconds);
        }

        backgroundCircle.setFill(isReadyToActivate() ? Color.DARKCYAN : Color.DARKSLATEGREY);

        if (isReadyToActivate()) {
            timersText.setText("");
            timersText.setVisible(false);
        } else {
            timersText.setText((isActive ? (int)currentActiveCountdown : (int)currentCooldown) + " s");
            timersText.setFill((isActive ? Color.WHITE : Color.GRAY));
            timersText.setVisible(true);
        }
    }
}
