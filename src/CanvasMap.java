import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CanvasMap {

    private ArrayList<ArrayList<Tile>> tileMatrix = new ArrayList<>();

    public CanvasMap(int h, int w) {
        for (int i = 0; i < h; i++) {
            ArrayList<Tile> column = new ArrayList<>();
            for (int j = 0; j < w; j++) {
                column.add(new Tile("tiles/grass.png"));
            }
            tileMatrix.add(column);
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

    public Tile setTile(String path, int x, int y) {
        if (x < tileMatrix.size()) {
            if (y < tileMatrix.get(x).size()) {
                Tile tile = tileMatrix.get(x).get(y);
                if (tile.getPath().contains(path)){
                    return null;
                }
                tileMatrix.get(x).set(y, new Tile(path));
                return tile;
            }
        }
        return null;
    }

    public void setTile(Tile tile, int x, int y) {
        tileMatrix.get(x).set(y, tile);
    }

    public void exportMapToPng(File file) {
        WritableImage writableImage = new WritableImage(
                tileMatrix.size() * Tile.TILE_WIDTH,
                tileMatrix.get(0).size() * Tile.TILE_HEIGHT
        );

        for (int i = 0; i < tileMatrix.size(); i++) {
            for (int j = 0; j < tileMatrix.get(i).size(); j++) {
                writeImageToWritableImage(
                        tileMatrix.get(i).get(j).getImage(),
                        writableImage,
                        i * Tile.TILE_WIDTH,
                        j * Tile.TILE_HEIGHT
                );
            }
        }

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), file.toString().split("\\.")[1], file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeImageToWritableImage(Image imageToWrite, WritableImage writableImage, int startX, int startY) {
        for (int x = 0; x < imageToWrite.getWidth(); x++) {
            for (int y = 0; y < imageToWrite.getHeight(); y++) {
                writableImage.getPixelWriter().setColor(
                        startX + x,
                        startY + y,
                        imageToWrite.getPixelReader().getColor(x, y));
            }
        }
    }

    public void exportMapToText(File file) throws FileNotFoundException {
        HashMap<String, String> pathToCodeMap = new HashMap<>();

        for (int i = 0; i < tileMatrix.size(); i++) {
            for (int j = 0; j < tileMatrix.get(i).size(); j++) {
                String p = tileMatrix.get(i).get(j).getPath();
                if (!pathToCodeMap.keySet().contains(p)) {
                    String len = String.valueOf(pathToCodeMap.values().size());
                    pathToCodeMap.put(p, len);
                }
            }
        }

        PrintWriter pw = new PrintWriter(file);

        for (String p : pathToCodeMap.keySet()) {
            pw.write(p + "," + pathToCodeMap.get(p) + "\n");
        }
        pw.write("\n");

        for (int i = 0; i < tileMatrix.size(); i++) {
            for (int j = 0; j < tileMatrix.get(i).size(); j++) {
                pw.write(pathToCodeMap.get(tileMatrix.get(i).get(j).getPath()) + ",");
            }
            pw.write("\n");
        }

        pw.close();
    }

    public void loadMapFromText(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
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
            String[] tileCodes = line.split(",");
            ArrayList<Tile> column = new ArrayList<>();
            for (String c : tileCodes) {
                String path1 = codeToPathMap.get(String.valueOf(c));
                String[] pathAttributes = path1.split("/");
                path1 = pathAttributes[1] + "/" + pathAttributes[2];
                column.add(new Tile(path1));
            }
            tileMatrix.add(column);
        }
    }
}
