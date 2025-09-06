package fpsgame.game.logic;

import com.badlogic.gdx.math.Vector2;

public class Player {
    private String name;
    private Vector2 spawn;
    private int health;
    private Weapon weapon; // Vũ khí hiện tại
    // private final float SIZE = 42f; // Increased by 50% (28f * 1.5 = 42f)
    private final float SPEED = 100f; // pixels per second
    private float rotation = 0f; // Player rotation angle
    private int charIndex;

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getSpeed() {
        return SPEED;
    }

    public int getCharIndex() {
        return charIndex;
    }

    public Vector2 getSpawn() {
        return spawn;
    }

    public void setSpawn(Vector2 spawn) {
        this.spawn = spawn;
    }

    

    

}
