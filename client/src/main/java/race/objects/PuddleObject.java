package race.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import race.MapObject;
import race.Player;
import services.managers.ResourceManager;

public class PuddleObject extends MapObject {

    public PuddleObject() {
        super(ResourceManager.obstaclesAtlas.findRegion("puddle"));
    }

    @Override
    public void onCollision(Player player) {
        player.slowDown();
        player.resetSprint();
    }
}
