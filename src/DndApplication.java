import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DndApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");

        // Create grid pane and add item
        DndEditor editor = new DndEditor(primaryStage);

        primaryStage.setScene(new Scene(editor));
        primaryStage.setFullScreen(true);

        primaryStage.show();
        editor.getCanvasHandler().draw();
    }
}
