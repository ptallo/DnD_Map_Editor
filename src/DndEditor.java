import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class DndEditor extends GridPane {
        private ItemOverlay itemOverlay;
        private CanvasHandler canvasHandler;

    public DndEditor(Stage primaryStage) {
        itemOverlay = new ItemOverlay(primaryStage, this);
        canvasHandler = new CanvasHandler(this, itemOverlay);
        itemOverlay.setCanvasHandler(canvasHandler);

        initializeRowColumnConstraints();

        add(itemOverlay.getMenuBar(), 0, 0, 2, 1);
        add(canvasHandler.getCanvas(), 0, 1, 1, 2);
        add(itemOverlay.getEditorMenu(), 1, 2);
    }

    public DndEditor(Stage primaryStage, File file) throws FileNotFoundException {
        this(primaryStage);
        canvasHandler.getCanvasMap().loadMapFromText(file);
        canvasHandler.draw();
    }

    public DndEditor(Stage primaryStage, int h, int w) {
        itemOverlay = new ItemOverlay(primaryStage, this);
        canvasHandler = new CanvasHandler(this, itemOverlay, h, w);
        itemOverlay.setCanvasHandler(canvasHandler);

        initializeRowColumnConstraints();

        add(itemOverlay.getMenuBar(), 0, 0, 2, 1);
        add(canvasHandler.getCanvas(), 0, 1, 1, 2);
        add(itemOverlay.getEditorMenu(), 1, 2);
    }

    private void initializeRowColumnConstraints() {
        RowConstraints menuRow = new RowConstraints(25);
        getRowConstraints().add(0, menuRow);

        RowConstraints canvasRow = new RowConstraints();
        canvasRow.setVgrow(Priority.ALWAYS);
        getRowConstraints().add(1, canvasRow);

        RowConstraints editorRow = new RowConstraints();
        editorRow.setPercentHeight(50);
        getRowConstraints().add(2, editorRow);

        ColumnConstraints canvasConstraint = new ColumnConstraints();
        canvasConstraint.setPercentWidth(85);

        ColumnConstraints editorConstraint = new ColumnConstraints();
        editorConstraint.setPercentWidth(15);

        getColumnConstraints().addAll(canvasConstraint, editorConstraint);
    }

    public CanvasHandler getCanvasHandler() {
        return canvasHandler;
    }
}
