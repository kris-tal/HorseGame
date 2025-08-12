package horsegame.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import horsegame.data.ItemType;

public class Item {
    private ItemType type;
    private Vector2 position;
    private boolean collected;

    public Item(ItemType type, Vector2 position) {
        this.type = type;
        this.position = position;
        this.collected = false;
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }

    public ItemType getType() {
        return type;
    }

    public void render(SpriteBatch batch) {
        if (!collected) {
            System.out.println("rendering" + type.displayName);
        }
    }

}
