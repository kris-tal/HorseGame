package race.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import race.MapObject;
import race.Player;
import services.managers.ResourceManager;

public class DeathStoneObject extends MapObject {
    public DeathStoneObject() {
        super(ResourceManager.obstaclesAtlas.findRegion("death_stone"));
    }

    @Override
    public void onCollision(Player player) {
        player.kill();
    }
}