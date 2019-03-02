import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class ItemOverlay extends ScrollPane {

    private MenuBar menuBar;
    private GridPane gridPane;
    private ListView listView;
    private Map<String, String> itemMap;
    private String activeTilePath;

    public ItemOverlay(Stage primaryStage, GridPane grid) {
        itemMap = new HashMap<>();
        itemMap.put("Dirt", "tiles/dirt.png");
        itemMap.put("Grass", "tiles/grass.png");
        gridPane = grid;
        createEditorMenu();
        createMenuBar(primaryStage);
    }

    void createMenuBar(Stage primaryStage) {
        // Create menubar object
        menuBar = new MenuBar();

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
        listView = new ListView();
        List<String> l = new ArrayList<String>(itemMap.keySet());
        ObservableList<String> observableList = FXCollections.observableList(l);
        listView.setItems(observableList);

        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        System.out.println("ListView selection changed from oldValue = "
                                + oldValue + " to newValue = " + newValue);
                        setActiveTilePath(oldValue.toString());
                        ImageView imageView = new ImageView();
                        imageView.setImage(new Image(new File("resources/" + activeTilePath).toURI().toString()));
                        gridPane.add(imageView, 1, 1);
                    }
                }

        );
    }

    void setActiveTilePath(String newValue) {
        activeTilePath = itemMap.get(newValue);
    }

    ListView getEditorMenu() {
        return listView;
    }

    void setEditorMode() {
    }

    MenuBar getMenuBar() {
        return menuBar;
    }
}
