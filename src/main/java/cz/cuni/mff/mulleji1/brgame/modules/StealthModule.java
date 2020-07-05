package cz.cuni.mff.mulleji1.brgame.modules;

import javafx.scene.image.Image;

public class StealthModule extends ActivatableSpaceShipModule {
    public StealthModule() {
        super("Stealth", new Image(StealthModule.class.getResourceAsStream("stealth_icon.png")));

        cooldown = 10;
        activeCountdown = 5;
    }

    @Override
    void activateInternal() {
        // logic to be implemented
        throw new UnsupportedOperationException();
    }
}
