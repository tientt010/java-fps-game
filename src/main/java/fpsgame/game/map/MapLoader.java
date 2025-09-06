package fpsgame.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MapLoader {
    private int[][] mapData;
    private int mapWidth;
    private int mapHeight;

    // Các loại tile
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int FLOOR = 2;
    public static final int SPAWN_POINT = 3;

    public void loadMap (String mapPath){
        try {
            FileHandle file = Gdx.files.internal(mapPath);
            String content = file.readString();
            String[] lines = content.split("\n");
            mapHeight = lines.length;
            mapWidth = lines[0].split(",").length;

            mapData = new int[mapHeight][mapWidth];

            for(int y = 0;y < mapHeight; y++){
                String[] tokens = lines[y].trim().split(",");
                for(int x = 0; x < mapWidth; x++){
                    mapData[y][x] = Integer.parseInt(tokens[x]);
                }
            }
            Gdx.app.log("Simple2DGame", "Map loaded: " + mapWidth + "x" + mapHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTile(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
            return WALL; // Biên của map luôn là tường
        }
        return mapData[y][x];
    }

    public boolean isWall(int x, int y) {
        return getTile(x, y) == WALL;
    }

    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }

    // Tìm điểm spawn cho player
    public float[] getPlayerSpawnPoint(float tileSize) {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (mapData[y][x] == SPAWN_POINT) {
                    return new float[]{x * tileSize + tileSize / 2, y * tileSize + tileSize / 2};
                }
            }
        }
        return new float[]{tileSize + tileSize/2, tileSize + tileSize/2}; // Default spawn

    }
}