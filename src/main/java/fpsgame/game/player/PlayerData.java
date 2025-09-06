package fpsgame.game.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerData {
    private TextureRegion bodyRegion;
    private TextureRegion leftArmRegion;
    private TextureRegion rightArmRegion;
    private Vector2 pos;
    private float rotation;
    private boolean active;
    private final float BODY_OFFSET_X = -25f;
    private final float BODY_OFFSET_Y = 0f;
    private final float RIGHT_ARM_OFFSET_X = 55f;  // Reduced offset
    private final float RIGHT_ARM_OFFSET_Y = -90f;    // Reduced offset
    private final float LEFT_ARM_OFFSET_X = 60f;  // Reduced offset
    private final float LEFT_ARM_OFFSET_Y = 75f;   // Reduced offset


    public PlayerData(Vector2 pos) {
        this.bodyRegion = null;
        this.leftArmRegion = null;
        this.rightArmRegion = null;
        this.pos = pos;
        this.rotation = 0f;
        this.active = true;
    }
    public void setBodyRegion(TextureRegion bodyRegion) {
        this.bodyRegion = bodyRegion;
    }

    public void setLeftArmRegion(TextureRegion leftArmRegion) {
        this.leftArmRegion = leftArmRegion;
    }

    public void setRightArmRegion(TextureRegion rightArmRegion) {
        this.rightArmRegion = rightArmRegion;
    }

    

    

    public TextureRegion getBodyRegion() {
        return bodyRegion;
    }

    public TextureRegion getLeftArmRegion() {
        return leftArmRegion;
    }

    public TextureRegion getRightArmRegion() {
        return rightArmRegion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getBody_Offset_X() {
        return BODY_OFFSET_X;
    }

    public float getBody_Offset_Y() {
        return BODY_OFFSET_Y;
    }

    public float getRight_Arm_Offset_X() {
        return RIGHT_ARM_OFFSET_X;
    }

    public float getRight_Arm_Offset_Y() {
        return RIGHT_ARM_OFFSET_Y;
    }

    public float getLeft_Arm_Offset_X() {
        return LEFT_ARM_OFFSET_X;
    }

    public float getLeft_Arm_Offset_Y() {
        return LEFT_ARM_OFFSET_Y;
    }
}
