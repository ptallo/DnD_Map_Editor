import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DndEditor extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");

        CanvasHandler handler = new CanvasHandler();

        // Create grid pane and add item
        GridPane gridPane = new GridPane();
        ItemOverlay itemOverlay = new ItemOverlay(primaryStage, gridPane);

        RowConstraints menuRow = new RowConstraints(25);
        // menuRow.setVgrow(Priority.NEVER);
        RowConstraints canvasRow = new RowConstraints();
        canvasRow.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(0, menuRow);
        gridPane.getRowConstraints().add(1, canvasRow);



        gridPane.add(itemOverlay.getMenuBar(), 0, 0 , 2, 1);
        handler.draw();
        gridPane.add(handler.getCanvas(), 0, 1);

        //gridPane.getChildren().remove(handler.getCanvas());

        // For debugging
        gridPane.setGridLinesVisible(true);

        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();

        // set full screen
        primaryStage.setFullScreen(true);
    }
}
