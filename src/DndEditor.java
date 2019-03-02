import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DndEditor extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();

        CanvasHandler handler = new CanvasHandler();
        ItemOverlay itemOverlay = new ItemOverlay();

        HBox hBox = new HBox();
        hBox.getChildren().add(handler.getCanvas());
        hBox.getChildren().add(itemOverlay);
        root.getChildren().add(hBox);

        handler.draw();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // set full screen
        primaryStage.setFullScreen(true);
    }
}
