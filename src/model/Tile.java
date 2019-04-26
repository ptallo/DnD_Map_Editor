package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;

public class Tile implements Serializable {
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    private String path;
    private Image image;
    private Boolean pathable;

    public Tile(String path) {
        this.path = "resources/" + path;
        this.image = new Image(new File(this.path).toURI().toString());
        this.pathable = true;
    }

    public void draw(GraphicsContext gc, int x, int y) {
        gc.drawImage(image, x * TILE_WIDTH, y * TILE_HEIGHT);
    }

    public Image getImage() {
        return image;
    }

    public String getPath() {
        return path;
    }
}
