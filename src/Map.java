import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Map {

    private ArrayList<ArrayList<Tile>> tileMatrix = new ArrayList<>();

    public Map() {
        for (int i = 0; i < 10; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(new Tile("tiles/grass.png"));
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
}
