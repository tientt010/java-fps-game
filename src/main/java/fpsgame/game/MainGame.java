package fpsgame.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fpsgame.game.logic.Bullet;
import fpsgame.game.logic.Player;
import fpsgame.game.map.MapLoader;
import fpsgame.game.player.PlayerData;
import fpsgame.game.player.PlayerRenderer;

public class MainGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private MapLoader mapLoader;

    private ArrayList<Bullet> bullets;

    private BitmapFont font;
    private float fpsUpdateTimer = 0f;
    private int currentFPS = 0;

    private Player curPlayer;
    private PlayerData curPlayerData;
    private ArrayList<Player> othPlayers;
    private ArrayList<PlayerData> playerDatas;

    private Texture wallTexture;
    private Texture floorTexture;
    private Texture bulletTexture;

    private PlayerRenderer playerRenderer;
    private final float TILE_SIZE = 32f;
    
    @Override
    public void create () {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
        
        mapLoader = new MapLoader();
        mapLoader.loadMap("maps/level1.csv");
        
        createTextures();
        curPlayer = new Player();
        othPlayers = new ArrayList<>();

        bullets = new ArrayList<>();

        float[] spawn = mapLoader.getPlayerSpawnPoint(TILE_SIZE);
        curPlayer.setSpawn(new Vector2(spawn[0], spawn[1]));

        playerRenderer = new PlayerRenderer();
        playerRenderer.loadCharacterParts(1, curPlayer.getSpawn());
        for (Player p : othPlayers) {
            playerRenderer.loadCharacterParts(p.getCharIndex(), p.getSpawn());
        }
        
        playerDatas = playerRenderer.getPlayerDatas();
        curPlayerData = playerDatas.get(0); // Giả sử player hiện tại là người đầu tiên trong danh sách
        font = new BitmapFont();
        
        Gdx.app.log("Simple2DGame", "Game 2D started!");
    }

    private void createTextures() {
        // Thử load texture từ file trước, nếu không có thì tạo placeholder
        try {
            wallTexture = new Texture(Gdx.files.internal("textures/wall.png"));
            Gdx.app.log("Simple2DGame", "Loaded wall texture from file: wall.png");
            
            // Load floor texture
            floorTexture = new Texture(Gdx.files.internal("textures/floor.png"));
            Gdx.app.log("Simple2DGame", "Loaded floor texture from file: floor.png");
            
            // Load bullet texture
            bulletTexture = new Texture(Gdx.files.internal("textures/bullet.png"));
            Gdx.app.log("Simple2DGame", "Loaded bullet texture: bullet.png");
        } catch (Exception e) {
            Gdx.app.error("Simple2DGame", "Error loading textures: " + e.getMessage());
        }
    }

    @Override
    public void render () {
        float deltaTime = Gdx.graphics.getDeltaTime();
        handleInput(deltaTime);

        // Update FPS counter
        fpsUpdateTimer += deltaTime;
        if (fpsUpdateTimer >= 1.0f) { // Update every second
            currentFPS = Gdx.graphics.getFramesPerSecond();
            fpsUpdateTimer = 0f;
            // Also log to console
            Gdx.app.log("FPS", "Current FPS: " + currentFPS);
        }

        Vector2 mousePos = getWorldMousePosition();
        float deltaX = mousePos.x - curPlayerData.getPos().x;
        float deltaY = mousePos.y - curPlayerData.getPos().y;
        curPlayerData.setRotation((float) Math.toDegrees(Math.atan2(deltaY, deltaX)));

        updateBullets(deltaTime);

        // Clear screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Update camera để follow player
        camera.position.set(curPlayerData.getPos().x, curPlayerData.getPos().y, 0);
        camera.update();

        // Chỗ này không hiểu lắm@@
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        renderMap();
        playerRenderer.render(batch);
        renderBullets();
        
        batch.end();

        // Vẽ HUD (FPS counter)
        // Render FPS on screen with UI camera
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        // Convert screen position to world coordinates for FPS display
        Vector2 screenTopLeft = new Vector2(10, Gdx.graphics.getHeight() - 30);
        Vector3 worldTopLeft = camera.unproject(new Vector3(screenTopLeft.x, screenTopLeft.y, 0));

        font.draw(batch, "FPS: " + currentFPS, worldTopLeft.x, worldTopLeft.y);
        
        batch.end();
    }

    private void renderMap(){
        int mapWidth = mapLoader.getMapWidth();
        int mapHeight = mapLoader.getMapHeight();

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int tileType = mapLoader.getTile(x, y);
                
                float drawX = x * TILE_SIZE;
                float drawY = y * TILE_SIZE;
                
                if(tileType == MapLoader.WALL) {
                    batch.draw(wallTexture, drawX, drawY);
                }else{
                    batch.draw(floorTexture, drawX, drawY);
                }
            }
        }
    }


    private void renderBullets() {
        for (Bullet bullet : bullets) {
            if (bullet.isActive()) {
                Vector2 bulletPos = bullet.getPos();
                float bulletRenderSize = bullet.getBulletRenderSize(); // Sử dụng render size to hơn
                float rotation = bullet.getRotation(); // Lấy góc xoay
                
                // Tạo TextureRegion từ Texture để có thể sử dụng rotation
                TextureRegion bulletRegion = new TextureRegion(bulletTexture);
                
                // Vẽ viên đạn với rotation
                batch.draw(bulletRegion, 
                          bulletPos.x - bulletRenderSize/2,  // x
                          bulletPos.y - bulletRenderSize/2,  // y 
                          bulletRenderSize/2,                // originX (center)
                          bulletRenderSize/2,                // originY (center)
                          bulletRenderSize,                  // width
                          bulletRenderSize,                  // height
                          1.0f,                              // scaleX
                          1.0f,                              // scaleY
                          rotation);                         // rotation (degrees)
            }
        }
    }

    private void updateBullets(float deltaTime) {
        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(deltaTime);
            
            // Check collision với tường
            Vector2 bulletPos = bullet.getPos();
            int tileX = (int) (bulletPos.x / TILE_SIZE);
            int tileY = (int) (bulletPos.y / TILE_SIZE);

            if (mapLoader.isWall(tileX, tileY) || bulletPos.x < -100 || bulletPos.x > mapLoader.getMapWidth() * TILE_SIZE + 100 ||
                bulletPos.y < -100 || bulletPos.y > mapLoader.getMapHeight() * TILE_SIZE + 100) {
                bullet.destroy();
            } 

            // Remove inactive bullets
            if (!bullet.isActive()) {
                bulletIterator.remove();
            } 
        }
    }

    private Vector2 getWorldMousePosition() {
        // Convert screen coordinates to world coordinates
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector3 worldPos3 = camera.unproject(new Vector3(mousePos.x, mousePos.y, 0));
        return new Vector2(worldPos3.x, worldPos3.y);
    }

    private void handleInput(float deltaTime) {
        Vector2 movement = new Vector2();

        // WASD movement
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.y += curPlayer.getSpeed() * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movement.y -= curPlayer.getSpeed() * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.x -= curPlayer.getSpeed() * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.x += curPlayer.getSpeed() * deltaTime;
        }

        // Collision detection
        Vector2 newPosition = new Vector2(curPlayerData.getPos()).add(movement);

        // Kiểm tra collision với tường
        int tileX = (int) (newPosition.x / TILE_SIZE);
        int tileY = (int) (newPosition.y / TILE_SIZE);

        if (!mapLoader.isWall(tileX, tileY)) {
            curPlayerData.setPos(newPosition);
        }

        // ESC để thoát
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Bắn đạn khi nhấn chuột trái
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // Tạo viên đạn mới
            Bullet newBullet = new Bullet();
            
            // Bắn theo hướng player đang xoay (playerRotation)

            newBullet.fire(curPlayerData.getPos().x, curPlayerData.getPos().y, curPlayerData.getRotation());

            // Thêm vào danh sách bullets
            bullets.add(newBullet);

            Gdx.app.log("Fps Game", "Bullet fired! Direction: " + curPlayer.getRotation() + " degrees");
        }
    }
}
