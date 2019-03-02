import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DndEditor extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();

        Canvas canvas = new Canvas(1000, 650);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ItemOverlay itemOverlay = new ItemOverlay();

        HBox hBox = new HBox();
        hBox.getChildren().add(canvas);
        hBox.getChildren().add(itemOverlay);

        drawOnCanvas(gc);
        root.getChildren().add(hBox);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // set full screen
        primaryStage.setFullScreen(true);
    }

    private void drawOnCanvas(GraphicsContext gc) {
        Map map = new Map();
        map.draw(gc);
    }
}
