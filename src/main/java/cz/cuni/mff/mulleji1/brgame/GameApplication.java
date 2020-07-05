package cz.cuni.mff.mulleji1.brgame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameApplication  extends Application {
    /**
     * Entry point of application
     */
    @Override
    public void start(Stage stage) {
        Group g = new Group();
        Scene s = new Scene(g);

        GameLoop loop = new GameLoop(g);

        stage.setScene(s);

        // start gameloop timer
        loop.start();

        stage.show();
    }
}
