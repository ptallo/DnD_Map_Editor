import javafx.application.Platform;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.File;
import java.security.PrivateKey;
import java.util.*;

public class ItemOverlay extends ScrollPane {

    private MenuBar menuBar;
    private GridPane gridPane;
    private ListView listView;
    private Map<String, String> itemMap;
    private String activeTilePath = "tiles/dirt.png";
    private CanvasMap canvasMap;

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

        MenuItem export = new MenuItem("Export Map");
        MenuItem load = new MenuItem("Load Map");
        MenuItem save = new MenuItem("Save Map");

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Map");
                fileChooser.setInitialFileName("Map");
            }
        });

        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(() ->{
                    ButtonType yes = new ButtonType("Yes");
                    ButtonType no = new ButtonType("No");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save?", yes, no);
                    alert.initOwner(primaryStage);
                    alert.setTitle("Save");
                    alert.setHeaderText("Save map to mapImage.png");
                    alert.setResizable(true);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == yes) {
                            alert.close();
                            canvasMap.exportMap("mapImage.png");
                        } else {
                            alert.close();
                        }
                    });
                });
            }
        });


        editorMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setEditorMode();
            }
        });

        // Add submenus to parent button
        file.getItems().addAll(save, load, export);
        view.getItems().addAll(gameMode, editorMode);

        // Add to menu bar
        menuBar.getMenus().addAll(file, edit, view);
        menuBar.prefHeight(25);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    }

    private void createEditorMenu() {
        listView = new ListView();
        List<String> l = new ArrayList<String>(itemMap.keySet());
        ObservableList<String> observableList = FXCollections.observableList(l);
        listView.setItems(observableList);
        listView.setPrefHeight(observableList.size() * 24 + 2);

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    setActiveTilePath(newValue.toString());
                    ImageView imageView = new ImageView();
                    imageView.setImage(new Image(new File("resources/" + activeTilePath).toURI().toString()));
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(200);
                    gridPane.add(imageView, 1, 1);
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

    public void setCanvasMap(CanvasMap cmap){
        canvasMap = cmap;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public String getActiveTilePath() {
        return activeTilePath;
    }
}
