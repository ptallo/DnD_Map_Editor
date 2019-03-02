import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class CanvasHandler {
    private Canvas canvas;
    private GraphicsContext gc;
    private CanvasMap canvasMap;
    private ItemOverlay overlay;

    private Double dragX;
    private Double dragY;

    public CanvasHandler(GridPane gp, ItemOverlay overlay) {
        this.canvas = new Canvas(1000, 1000);
        this.gc = canvas.getGraphicsContext2D();
        this.canvasMap = new CanvasMap();
        this.overlay = overlay;

        canvas.heightProperty().bind(gp.heightProperty().subtract(25));

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
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
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            this.dragX = null;
            this.dragY = null;
        });

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            canvasMap.setTile(overlay.getActiveTilePath(),
                    (int) event.getX() / Tile.TILE_WIDTH,
                    (int) event.getY() / Tile.TILE_HEIGHT
            );
        });
    }

    public void draw() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), canvas.getWidth(), canvas.getHeight());
                canvasMap.draw(gc);
            }
        }.start();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
