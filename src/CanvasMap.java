import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CanvasMap {

    private ArrayList<ArrayList<Tile>> tileMatrix = new ArrayList<>();

    public CanvasMap() {
        for (int i = 0; i < 50; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                if (i == 0 || j == 0 || i == 49 || j == 49){
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
}
