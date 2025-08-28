package race;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class MapObject {
    private final Texture texture;

    public MapObject(Texture texture) {
        this.texture = texture;
    }

    public void update(float delta) {}

    public abstract void onCollision(Player player);

    public void render(SpriteBatch batch, float x, float y, float size) {
        if (texture != null)
            batch.draw(texture, x, y, size, size);
    }
}
