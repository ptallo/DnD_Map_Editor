package gui;

import gui.StartMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DndApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");

        // Create grid pane and add item
        StartMenu startMenu = new StartMenu(primaryStage);
//        gui.DndEditor editor = new gui.DndEditor(primaryStage);

        primaryStage.setScene(new Scene(startMenu));
//        primaryStage.setFullScreen(true);

        primaryStage.show();
//        editor.getCanvasHandler().draw();
    }
}
