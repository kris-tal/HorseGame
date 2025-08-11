package horsegame.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import horsegame.data.HorseType;

public class Horse {
    private HorseType type;
    private Vector2 position;
    private float friendshipLevel; // 0 do 100.0
    private boolean isWild;

    public Horse(HorseType type, Vector2 position, boolean isWild) {
        this.type = type;
        this.position = position;
        this.friendshipLevel = 0;
        this.isWild = isWild;
    }

    public void tame() {
        isWild = false;
    }

    public void give(String giftId) {
        if (type.giftPreferences.contains(giftId)) {
            friendshipLevel = Math.min(100f, friendshipLevel + 10f);
        }
        else {
            friendshipLevel = Math.max(0f, friendshipLevel - 5f);
        }
    }

    // no i on bedzie mial jedna z animacji no ale to nwm jak zrobic na razie

    public void render(SpriteBatch batch) {

    }

    // gettery
    public HorseType getType() { return type; }
    public Vector2 getPosition() { return position; }
    public float getFriendshipLevel() { return friendshipLevel; }
}
