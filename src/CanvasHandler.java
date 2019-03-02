import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;


public class CanvasHandler {
    private Canvas canvas;
    private GraphicsContext gc;

    private Double dragX;
    private Double dragY;

    public CanvasHandler() {
        this.canvas = new Canvas(1000, 650);
        this.gc = canvas.getGraphicsContext2D();

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (dragX == null || dragY == null) {
                dragX = event.getX();
                dragY = event.getY();
            } else {
                gc.translate(event.getX() - dragX, event.getY() - dragY);
                dragX = event.getX();
                dragY = event.getY();
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            this.dragX = null;
            this.dragY = null;
        });
    }

    public void draw() {
        Map map = new Map();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(-gc.getTransform().getTx(), -gc.getTransform().getTy(), canvas.getWidth(), canvas.getHeight());
                map.draw(gc);
            }
        }.start();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
