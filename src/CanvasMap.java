import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

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

    public void saveMap(String path) throws FileNotFoundException {
        HashMap<String, String> pathToCodeMap = new HashMap<>();

        for (ArrayList<Tile> aTileMatrix : tileMatrix) {
            for (int j = 0; j < aTileMatrix.size(); j++) {
                String p = aTileMatrix.get(j).getPath();
                if (!pathToCodeMap.keySet().contains(p)) {
                    String len = String.valueOf(pathToCodeMap.values().size());
                    pathToCodeMap.put(p, len);
                }
            }
        }

        File file = new File("resources/maps/" + path);
        PrintWriter pw = new PrintWriter(file);

        for (String p : pathToCodeMap.keySet()) {
            pw.write(p + "," + pathToCodeMap.get(p) + "\n");
        }
        pw.write("\n");

        for (ArrayList<Tile> row : tileMatrix) {
            for (Tile tile : row) {
                pw.write(pathToCodeMap.get(tile.getPath()));
            }
            pw.write("\n");
        }

        pw.close();
    }

    public void loadMap(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("resources/maps/" + path));
        HashMap<String, String> codeToPathMap = new HashMap<>();

        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            if (line.equals("")) {
                break;
            }
            String[] splitA = line.split(",");
            codeToPathMap.put(splitA[1], splitA[0]);
        }

        tileMatrix = new ArrayList<>();
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            ArrayList<Tile> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                String path1 = codeToPathMap.get(String.valueOf(c));
                String[] pathAttributes = path1.split("/");
                path1 = pathAttributes[1] + "/" + pathAttributes[2];
                row.add(new Tile(path1));
            }
            tileMatrix.add(row);
        }
    }
}
