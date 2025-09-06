package fpsgame.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerRenderer {
    private TextureAtlas atlas;
    private ArrayList<PlayerData> playerDatas;

    private final float SCALE = 0.11f;
    // private final float SCALE = 1f;

    public PlayerRenderer() {
        this.atlas = new TextureAtlas("character.atlas");
        playerDatas = new ArrayList<>();
        // loadCharacterParts();
    }

    public void loadCharacterParts(int charIndex, Vector2 pos){
        PlayerData playerData = new PlayerData(pos);
        playerData.setBodyRegion(atlas.findRegion("body_" + charIndex));
        playerData.setLeftArmRegion(atlas.findRegion("arm_left_long_" + charIndex));
        playerData.setRightArmRegion(atlas.findRegion("arm_right_short_" + charIndex));
        playerDatas.add(playerData);
    }

    public void render(SpriteBatch batch){
        for (PlayerData pd : playerDatas) {
            if (!pd.isActive()) continue;

            float centerX = pd.getPos().x;
            float centerY = pd.getPos().y;
            float rotation = pd.getRotation();

            float cos = (float) Math.cos(Math.toRadians(rotation));
            float sin = (float) Math.sin(Math.toRadians(rotation));

            // Tay trái
            float leftArmLocalX = pd.getLeft_Arm_Offset_X() * SCALE;
            float leftArmLocalY = pd.getLeft_Arm_Offset_Y() * SCALE;
            float leftArmRotatedX = leftArmLocalX * cos - leftArmLocalY * sin;
            float leftArmRotatedY = leftArmLocalX * sin + leftArmLocalY * cos;
            renderPart(batch, pd.getLeftArmRegion(), centerX + leftArmRotatedX, centerY + leftArmRotatedY, rotation);

            // Tay phải
            float rightArmLocalX = pd.getRight_Arm_Offset_X() * SCALE;
            float rightArmLocalY = pd.getRight_Arm_Offset_Y() * SCALE;
            float rightArmRotatedX = rightArmLocalX * cos - rightArmLocalY * sin;
            float rightArmRotatedY = rightArmLocalX * sin + rightArmLocalY * cos;
            renderPart(batch, pd.getRightArmRegion(),
                  centerX + rightArmRotatedX,
                  centerY + rightArmRotatedY,
                  rotation);

            // Thân
            float bodyLocalX = pd.getBody_Offset_X() * SCALE;
            float bodyLocalY = pd.getBody_Offset_Y() * SCALE;
            float bodyRotatedX = bodyLocalX * cos - bodyLocalY * sin;
            float bodyRotatedY = bodyLocalX * sin + bodyLocalY * cos;
            renderPart(batch, pd.getBodyRegion(),
                  centerX + bodyRotatedX,
                  centerY + bodyRotatedY,
                  rotation);
        }
    }

    private void renderPart(SpriteBatch batch, TextureRegion region, 
                           float x, float y, float rotation) {
        float width = region.getRegionWidth() * SCALE;
        float height = region.getRegionHeight() * SCALE;

        batch.draw(region,
                  x - width/2,           // x position (centered)
                  y - height/2,          // y position (centered) 
                  width/2,               // origin X (center)
                  height/2,              // origin Y (center)
                  width,                 // width
                  height,                // height
                  1.0f,                  // scale X
                  1.0f,                  // scale Y
                  rotation);             // rotation
    }

    public ArrayList<PlayerData> getPlayerDatas() {
        return playerDatas;
    }
    
}
