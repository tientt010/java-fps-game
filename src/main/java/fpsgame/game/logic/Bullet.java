package fpsgame.game.logic;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 pos;
    private Vector2 velocity;
    private boolean active;
    private float rotation;

    public float getRotation() {
        return rotation;
    }

    private final float BULLET_SPEED = 300f; // pixels per second
    private final float BULLET_SIZE = 12f; // kích thước collision (tăng 50%: 8f * 1.5 = 12f)
    private final float BULLET_RENDER_SIZE = 24f; // kích thước hiển thị (tăng 50%: 12f * 1.5 = 18f)
    
    public float getBulletRenderSize() {
        return BULLET_RENDER_SIZE;
    }

    public Bullet() {
        pos = new Vector2();
        velocity = new Vector2();
        active = false;
        rotation = 0f;
    }

    public void fire(float startX, float startY, float directionAngle) {
        pos.set(startX, startY);
        
        // Lưu góc xoay cho việc rendering
        rotation = directionAngle;
        
        // Tính toán velocity dựa trên góc bắn
        float radians = (float) Math.toRadians(directionAngle);
        velocity.set(
            (float) Math.cos(radians) * BULLET_SPEED,
            (float) Math.sin(radians) * BULLET_SPEED
        );
        
        active = true;
    }

    public void update(float deltaTime) {
        if (!active) return;
        pos.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }

    public void destroy() {
        active = false;
    }

    public Vector2 getPos() {
        return pos;
    }

    public boolean isActive() {
        return active;
    }

    
}
