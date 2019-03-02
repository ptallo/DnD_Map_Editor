import javafx.scene.canvas.GraphicsContext;

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
        for (int i = 0; i < tileMatrix.size(); i++) {
            for (int j = 0; j < tileMatrix.get(i).size(); j++) {
                tileMatrix.get(i).get(j).draw(gc, i, j);
            }
        }
    }
}
