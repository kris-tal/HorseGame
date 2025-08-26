package data;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Horse {
    private HorseData data;
    private Vector2 position;
    private float friendshipLevel; // 0 do 100.0
    private boolean isWild; // For now no wild horses

    public Horse(HorseData type, Vector2 position, boolean isWild) {
        this.data = type;
        this.position = position;
        this.friendshipLevel = 0;
        this.isWild = isWild;
    }

    public void tame() {
        isWild = false;
    }

    public void give(String giftId) {
        if (true){//data.giftPreferences.contains(giftId)) {
            friendshipLevel = Math.min(100f, friendshipLevel + 10f);
        }
        else {
            friendshipLevel = Math.max(0f, friendshipLevel - 5f);
        }
    }

    // gettery
    public HorseData getData() { return data; }
    public Vector2 getPosition() { return position; }
    public float getFriendshipLevel() { return friendshipLevel; }
}
