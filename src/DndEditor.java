import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.File;

public class DndEditor extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");

        // Create grid pane and add item
        GridPane gridPane = new GridPane();
        ItemOverlay itemOverlay = new ItemOverlay(primaryStage, gridPane);
        CanvasHandler handler = new CanvasHandler(gridPane, itemOverlay);

        RowConstraints menuRow = new RowConstraints(25);
        gridPane.getRowConstraints().add(0, menuRow);

        RowConstraints canvasRow = new RowConstraints();
        canvasRow.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(1, canvasRow);

        gridPane.add(itemOverlay.getMenuBar(), 0, 0 , 2, 1);
        handler.draw();
        gridPane.add(handler.getCanvas(), 0, 1, 1, 2);
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new File("resources/tiles/dirt.png").toURI().toString()));
        gridPane.add(imageView, 1, 1);
        gridPane.add(itemOverlay.getEditorMenu(), 1, 2);

        handler.draw();

        // For debugging
        gridPane.setGridLinesVisible(true);

        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();

        // set full screen
        primaryStage.setFullScreen(true);
    }
}
