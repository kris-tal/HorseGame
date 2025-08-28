package race;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Player {
    private Texture texture;
    private int lane;
    private float col = 0f;
    private int coins = 0;
    private final float speed;
    private boolean alive = true;

    public Player(Texture texture, int startingLane, float speed) {
        this.texture = texture;
        lane = startingLane;
        this.speed = speed;
    }

    public void moveUp() {
        if (lane < 2)
            lane++;
    }

    public void moveDown() {
        if (lane > 0)
            lane--;
    }

    public void update(float delta) {
        col += delta * speed;
    }

    public void render(SpriteBatch batch, float x, float y, float size) {
        batch.draw(texture, x, y, size, size);
    }

    // getters / setters

    public int getLane() {
        return lane;
    }

    public float getCol() {
        return col;
    }

    public boolean isAlive() {
        return alive;
    }

    public float getSpeed() {
        return speed;
    }
    public int getCoins() {
        return coins;
    }

    public void kill() {
        alive = false;
    }

    public void addCoins(int amount) {
        coins += amount;
    }
}
