import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class CanvasHandler {
    private Canvas canvas;
    private GraphicsContext gc;

    public CanvasHandler() {
        this.canvas = new Canvas(1000, 650);
        this.gc = canvas.getGraphicsContext2D();
    }

    public void draw() {
        Map map = new Map();
        map.draw(gc);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
