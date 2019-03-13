import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Stack;


public class CanvasHandler {
    private Canvas canvas;
    private GraphicsContext gc;
    private CanvasMap canvasMap;
    private ItemOverlay overlay;

    private Double dragX;
    private Double dragY;

    private Stack<Point2D> undoStack;
    private HashMap<Point2D, Tile> undoHashMap;

    private Boolean ctrlPressed;

    public CanvasHandler(GridPane gp, ItemOverlay overlay) {
        this.canvas = new Canvas(1000, 1000);
        this.gc = canvas.getGraphicsContext2D();
        this.canvasMap = new CanvasMap();
        this.overlay = overlay;
        this.undoStack = new Stack<>();
        this.undoHashMap = new HashMap<>();

        canvas.heightProperty().bind(gp.heightProperty().subtract(25));
        canvas.widthProperty().bind(gp.widthProperty().multiply(overlay.getMode() == 1 ? 0.85 : 1));
        canvas.setFocusTraversable(true);

        addMouseEventListeners(overlay);

        addKeyEventListeners();
    }

    private void addKeyEventListeners() {
        canvas.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.Z && ctrlPressed) {
                if (!undoStack.empty()) {
                    Point2D point2D = undoStack.pop();
                    Tile tile = undoHashMap.get(point2D);
                    canvasMap.setTile(tile, (int) point2D.getX(), (int) point2D.getY());
                    draw();
                }
            } else if (event.getCode() == KeyCode.CONTROL) {
                ctrlPressed = true;
            }
        });

        canvas.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                ctrlPressed = false;
            }
        });
    }

    private void addMouseEventListeners(ItemOverlay overlay) {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.isControlDown()) {
                handleDragMap(event);
            } else {
                setTile(overlay, event);
            }
            draw();
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            this.dragX = null;
            this.dragY = null;
        });

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!event.isControlDown()) {
                setTile(overlay, event);
                draw();
            }
        });
    }

    private void setTile(ItemOverlay overlay, MouseEvent event) {
        int x = (int) (event.getX() - gc.getTransform().getTx()) / Tile.TILE_WIDTH;
        int y = (int) (event.getY() - gc.getTransform().getTy()) / Tile.TILE_HEIGHT;
        Tile tile = canvasMap.setTile(overlay.getActiveTilePath(),
                x,
                y
        );

        if (tile != null) {
            Point2D point2D = new Point2D(x, y);
            undoStack.push(point2D);
            undoHashMap.put(point2D, tile);
        }
    }

    private void handleDragMap(MouseEvent event) {
        if (dragX == null || dragY == null) {
            dragX = event.getX();
            dragY = event.getY();
        } else {
            double x = event.getX() - dragX;
            double y = event.getY() - dragY;

            Rectangle canvasRect = new Rectangle(
                    -gc.getTransform().getTx() - x,
                    -gc.getTransform().getTy() - y,
                    canvas.getWidth(),
                    canvas.getHeight()
            );

            Rectangle mapRectangle = canvasMap.getMapRectangle();

            Shape s1 = Shape.intersect(canvasRect, mapRectangle);

            if (s1.getBoundsInLocal().getWidth() == mapRectangle.getBoundsInLocal().getWidth() ||
                    s1.getBoundsInLocal().getWidth() == canvasRect.getBoundsInLocal().getWidth()) {
                gc.translate(x, 0);
                dragX = event.getX();
            }

            if (s1.getBoundsInLocal().getHeight() == mapRectangle.getBoundsInLocal().getHeight() ||
                    s1.getBoundsInLocal().getHeight() == canvasRect.getBoundsInLocal().getHeight()) {
                gc.translate(0, y);
                dragY = event.getY();
            }
        }
    }

    public void draw() {
        gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), canvas.getWidth(), canvas.getHeight());
        canvasMap.draw(gc);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public CanvasMap getCanvasMap() {
        return canvasMap;
    }
}
