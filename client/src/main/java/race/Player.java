package race;

import animations.HorseAnimationActor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final HorseAnimationActor horse;
    private int lane;
    private float col = 0f;
    private int coins = 0;
    private final float speed;
    private boolean alive = true;

    public Player(HorseAnimationActor horse, int startingLane, float speed) {
        this.horse = horse;
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
        horse.setMoving(true);
        horse.act(delta);
    }

    public void render(SpriteBatch batch, float x, float y, float size) {
        horse.setPosition(x, y);
        horse.setSize(size, size);
        horse.draw(batch, 1f);
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
