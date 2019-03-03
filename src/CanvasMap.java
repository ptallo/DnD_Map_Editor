import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CanvasMap {

    private ArrayList<ArrayList<Tile>> tileMatrix = new ArrayList<>();

    public CanvasMap() {
        for (int i = 0; i < 50; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                if (i == 0 || j == 0 || i == 49 || j == 49) {
                    row.add(new Tile("tiles/dirt.png"));
                } else {
                    row.add(new Tile("tiles/grass.png"));
                }
            }
            tileMatrix.add(row);
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.GREY);
        for (int i = 0; i < tileMatrix.size(); i++) {
            for (int j = 0; j < tileMatrix.get(i).size(); j++) {
                tileMatrix.get(i).get(j).draw(gc, i, j);
                gc.strokeRect(i * Tile.TILE_WIDTH, j * Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            }
        }
    }

    public Rectangle getMapRectangle() {
        return new Rectangle(0, 0, tileMatrix.size() * Tile.TILE_WIDTH, tileMatrix.get(0).size() * Tile.TILE_HEIGHT);
    }

    public void setTile(String path, int x, int y) {
        tileMatrix.get(x).set(y, new Tile(path));
    }

    public void exportMap(String path) {
        WritableImage writableImage = new WritableImage(
                tileMatrix.size() * Tile.TILE_WIDTH,
                tileMatrix.get(0).size() * Tile.TILE_HEIGHT
        );

        for (int i = 0; i < tileMatrix.size(); i++) {
            for (int j = 0; j < tileMatrix.get(i).size(); j++) {
                writeImageAt(
                        writableImage,
                        tileMatrix.get(i).get(j).getImage(),
                        i * Tile.TILE_WIDTH,
                        j * Tile.TILE_HEIGHT
                );
            }
        }

        File writeTo = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), path.split("\\.")[1], writeTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeImageAt(WritableImage writableImage, Image test, int startX, int startY) {
        for (int x = 0; x < test.getWidth(); x++) {
            for (int y = 0; y < test.getHeight(); y++) {
                writableImage.getPixelWriter().setColor(
                        startX + x,
                        startY + y,
                        test.getPixelReader().getColor(x, y));
            }
        }
    }
}
