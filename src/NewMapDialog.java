import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class NewMapDialog extends VBox {
    private TextField wTextField;
    private TextField hTextField;
    private int spacing = 10;
    private int padding = 30;

    public NewMapDialog(Stage primaryStage) {
        setSpacing(spacing);
        setPadding(new Insets(padding, padding, padding, padding));

        Text wText = new Text("Width");
        wTextField = new TextField("20");
        HBox widthBox = new HBox(wText, wTextField);
        widthBox.setSpacing(spacing);

        Text hText = new Text("Height");
        hTextField = new TextField("20");
        HBox heightBox = new HBox(hText, hTextField);
        heightBox.setSpacing(10);

        Button createMapButton = getCreateMapButton(primaryStage);

        Button cancelButton = getCancelButton(primaryStage);

        getChildren().addAll(widthBox, heightBox, createMapButton, cancelButton);
    }

    private Button getCreateMapButton(Stage primaryStage) {
        Button createMapButton = new Button("Create Map");
        createMapButton.setMaxWidth(Double.MAX_VALUE);
        createMapButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            DndEditor editor = new DndEditor(
                    primaryStage, Integer.parseInt(wTextField.getText()),
                    Integer.parseInt(hTextField.getText())
            );

            primaryStage.setScene(new Scene(editor));
            primaryStage.setFullScreen(true);
            editor.getCanvasHandler().draw();
        });
        return createMapButton;
    }

    private Button getCancelButton(Stage primaryStage) {
        Button cancelButton = new Button("Cancel");
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            StartMenu startMenu = new StartMenu(primaryStage);
            primaryStage.setScene(new Scene(startMenu));
        });
        return cancelButton;
    }
}
