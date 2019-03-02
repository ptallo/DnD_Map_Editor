import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ItemOverlay extends ScrollPane {

    private MenuBar menuBar = new MenuBar();
    private GridPane gridPane;

    public ItemOverlay(Stage primaryStage, GridPane grid) {
        createMenuBar(primaryStage);
        gridPane = grid;
    }

    void createMenuBar(Stage primaryStage) {
        // Create menus
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu view = new Menu("View");

        // Create submenus
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioMenuItem editorMode = new RadioMenuItem("Editor Mode");
        RadioMenuItem gameMode = new RadioMenuItem("Game Mode");
        toggleGroup.getToggles().addAll(editorMode, gameMode);

        editorMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setEditorMode();
            }
        });

        // Add submenus to parent button
        view.getItems().addAll(gameMode, editorMode);

        // Add to menu bar
        menuBar.getMenus().addAll(file, edit, view);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    }

    void createEditorMenu() {
        RadioButton dirtButton = new RadioButton("Dirt");
        RadioButton grassButton = new RadioButton("Grass");
    }

    void setEditorMode() {
    }

    MenuBar getMenuBar() {
        return menuBar;
    }
}
