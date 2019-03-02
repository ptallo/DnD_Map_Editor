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
import java.security.PrivateKey;
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
        itemMap.put("Forest", "tiles/forest.png");
        itemMap.put("Grass", "tiles/grass.png");
        itemMap.put("Ocean", "tiles/ocean.png");
        itemMap.put("Road", "tiles/road.png");

        gridPane = grid;
        createEditorMenu();
        createMenuBar(primaryStage);
    }

    private void createMenuBar(Stage primaryStage) {
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

    private void createEditorMenu() {
        listView = new ListView();
        List<String> l = new ArrayList<String>(itemMap.keySet());
        ObservableList<String> observableList = FXCollections.observableList(l);
        listView.setItems(observableList);

        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        setActiveTilePath(newValue.toString());
                        ImageView imageView = new ImageView();
                        imageView.setImage(new Image(new File("resources/" + activeTilePath).toURI().toString()));
                        gridPane.add(imageView, 1, 1);
                    }
                }

        );
    }

    private void setActiveTilePath(String newValue) {
        activeTilePath = itemMap.get(newValue);
    }

    public ListView getEditorMenu() {
        return listView;
    }

    public void setEditorMode() {
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public String getActiveTilePath() {
        return activeTilePath;
    }
}
