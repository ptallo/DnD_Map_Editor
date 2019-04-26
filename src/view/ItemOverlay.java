package view;

import controller.CanvasHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class ItemOverlay extends ScrollPane {

    private CanvasHandler canvasHandler;
    private GridPane gridPane;
    private ImageView imageView;
    private ListView tileNames;
    private Map<String, String> tileMap;
    private MenuBar menuBar;
    private int mode = 1; // 0 is game; 1 is editor
    private Stage primaryStage;
    private String activeTilePath = "tiles/dirt.png";

    public ItemOverlay(Stage stage, GridPane grid) {
        // Create and Populate model.Tile Map
        tileMap = new HashMap<>();
        populateTileMap();

        // Give access to the grid and stage
        gridPane = grid;
        primaryStage = stage;

        // Create menus
        createEditorMenu();
        createMenuBar(primaryStage);
    }

    // Create the menu bar and set up listeners
    private void createMenuBar(Stage primaryStage) {
        // Create menubar object
        menuBar = new MenuBar();

        // Create menus
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu view = new Menu("View");

        // Create submenus for view
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioMenuItem editorMode = new RadioMenuItem("Editor Mode");
        RadioMenuItem gameMode = new RadioMenuItem("Game Mode");
        toggleGroup.getToggles().addAll(editorMode, gameMode);

        // Create submenus for file
        MenuItem export = new MenuItem("Export Map");
        MenuItem importTile = new MenuItem("Import model.Tile");
        MenuItem load = new MenuItem("Load Map");
        MenuItem save = new MenuItem("Save Map");

        // Set up listeners for menu bar options
        save.setOnAction(this::doSave);
        load.setOnAction(this::doLoad);
        export.setOnAction(this::doExport);
        importTile.setOnAction(this::importTile);
        editorMode.setOnAction(this::setEditorMode);
        gameMode.setOnAction(this::setGameMode);

        // Add submenus to parent button
        file.getItems().addAll(save, load, export, importTile);
        view.getItems().addAll(gameMode, editorMode);

        // Add to menu bar
        menuBar.getMenus().addAll(file, edit, view);
        menuBar.prefHeight(25);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    }

    private void createEditorMenu() {
        // Initialize tileNames
        tileNames = new ListView();
        // Create list of tile names from the keys of the map
        List<String> l = new ArrayList<String>(tileMap.keySet());
        // Create an observable list from the list l
        ObservableList<String> observableList = FXCollections.observableList(l);
        // Set the items of the listview equal to the names from the tilemap
        tileNames.setItems(observableList);
        // Set height... Not sure if this works as intended
        tileNames.setPrefHeight(observableList.size() * 24 + 2);

        //Set up listener for value change of list view
        tileNames.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    // Update the active tile path
                    setActiveTilePath(newValue.toString());
                    // Create image view object
                    imageView = new ImageView();
                    imageView.setImage(new Image(new File("resources/" + activeTilePath).toURI().toString()));
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(200);
                    // Add image to gridpane
                    gridPane.add(imageView, 1, 1, 2, 1);
                }

        );
    }

    // Perform the save
    private void doSave(ActionEvent event) {
        // Create file chooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Map");
        fileChooser.setInitialFileName("Map.txt");
        // Show file chooser and take outputted file
        File savedFile = fileChooser.showSaveDialog(primaryStage);

        if (savedFile != null) {
            try {
                // Save the file
                canvasHandler.getCanvasMap().exportMapToText(savedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Perform the load
    private void doLoad(ActionEvent event) {
        // Create file chooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Map");

        // Show file chooser and take the outputted file
        File loadFile = fileChooser.showOpenDialog(primaryStage);

        if (loadFile != null) {
            try {
                // load the map and redraw the canvas
                canvasHandler.getCanvasMap().loadMapFromText(loadFile);
                canvasHandler.draw();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Export the Map
    private void doExport(ActionEvent event) {
        // Create the file chooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Map");
        fileChooser.setInitialFileName("Map.png");

        // Show file chooser and take the outputted file
        File savedFile = fileChooser.showSaveDialog(primaryStage);

        if (savedFile != null) {
            canvasHandler.getCanvasMap().exportMapToPng(savedFile);
        }
    }

    // Import model.Tile
    private void importTile(ActionEvent event) {
        // Create the file chooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import model.Tile");

        // Show file chooser and take the outputted file
        File loadFile = fileChooser.showOpenDialog(primaryStage);
        if (loadFile != null) {
            try {
                // Create the corresponding file object
                File tempFile = new File("resources/tiles/" + loadFile.getName());
                Files.copy(loadFile.toPath(), tempFile.toPath());

                // Get the name of file without extension
                String tileName = tempFile.getName().split("\\.")[0];

                // Add name to tile map and tile names
                tileMap.put(tileName, "tiles/" + tempFile.getName());
                tileNames.getItems().add(tileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getActiveTilePath() {
        return activeTilePath;
    }

    public ListView getEditorMenu() {
        return tileNames;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public int getMode() {
        return mode;
    }

    private void populateTileMap() {
        File folder = new File("resources/tiles/");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    tileMap.put(file.getName().split("\\.")[0], "tiles/" + file.getName());
                }
            }
        }
    }

    private void setActiveTilePath(String newValue) {
        activeTilePath = tileMap.get(newValue);
    }

    public void setCanvasHandler(CanvasHandler cHandler) {
        canvasHandler = cHandler;
    }

    public void setEditorMode(ActionEvent event) {
        mode = 1;
    }

    public void setGameMode(ActionEvent event) {
        mode = 0;
        gridPane.getChildren().remove(canvasHandler.getCanvas());
        gridPane.getChildren().remove(this.getEditorMenu());
        gridPane.getChildren().remove(imageView);
        gridPane.add(canvasHandler.getCanvas(), 0, 1);
    }
}
