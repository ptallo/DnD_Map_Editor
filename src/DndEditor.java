import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class DndEditor extends Application {

    private Map map = new Map();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("RevUC 2019");
        Group root = new Group();
        Canvas canvas = new Canvas(1000, 680);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawOnCanvas(gc);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawOnCanvas(GraphicsContext gc) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                map.draw(gc);
            }
        }.start();
    }
}
