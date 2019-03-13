import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;


public class StartMenu extends VBox {

    private Stage primaryStage;
    private int padding = 30;
    private int spacing = 10;

    public StartMenu(Stage primaryStage) {
        this.primaryStage = primaryStage;

        setSpacing(spacing);
        setPadding(new Insets(padding, padding, padding, padding));

        Text menuTitle = new Text("DnD Map Editor");
        menuTitle.setFont(new Font("Courier New", 50));

        Button newMapButton = new Button("New Map");
        newMapButton.setMaxWidth(Double.MAX_VALUE);
        newMapButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("new map button");
        });

        Button loadMapButton = new Button("Load Map");
        loadMapButton.setMaxWidth(Double.MAX_VALUE);
        loadMapButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event ->  {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Load Map");
            File savedFile = chooser.showOpenDialog(primaryStage);

            if (savedFile != null) {
                try {
                    DndEditor editor = new DndEditor(primaryStage, savedFile);
                    primaryStage.setScene(new Scene(editor));
                    primaryStage.setFullScreen(true);
                    editor.getCanvasHandler().draw();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });

        getChildren().addAll(menuTitle, newMapButton, loadMapButton);
        for (Node child: getChildren()) {
            setVgrow(child, Priority.ALWAYS);
        }
    }

}
