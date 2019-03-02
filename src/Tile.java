import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class Tile {
    public final int TILE_WIDTH = 32;
    public final int TILE_HEIGHT = 32;

    private Image image;
    private Boolean pathable;


    public Tile(String path) {
        File temp = new File("resources/tiles/" + path);
        this.image = new Image(temp.toURI().toString());
        this.pathable = true;
    }

    public void draw(GraphicsContext gc, int x, int y) {
        gc.drawImage(image, x * TILE_WIDTH, y * TILE_HEIGHT);
    }
}
