import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class DndEditor extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");

        // Create grid pane and add item
        GridPane gridPane = new GridPane();
        ItemOverlay itemOverlay = new ItemOverlay(primaryStage, gridPane);
        CanvasHandler handler = new CanvasHandler(gridPane);

        RowConstraints menuRow = new RowConstraints(25);
        gridPane.getRowConstraints().add(0, menuRow);

        RowConstraints canvasRow = new RowConstraints();
        canvasRow.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(1, canvasRow);

        gridPane.add(itemOverlay.getMenuBar(), 0, 0 , 2, 1);
        gridPane.add(handler.getCanvas(), 0, 1);

        handler.draw();

        // For debugging
        gridPane.setGridLinesVisible(true);

        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();

        // set full screen
        primaryStage.setFullScreen(true);
    }
}
