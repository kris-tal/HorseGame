package race.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import race.MapObject;
import race.Player;
import services.managers.ResourceManager;

public class ElectricBarrierObject extends MapObject {

    private boolean destroyed = false;

    public ElectricBarrierObject() {
        super(ResourceManager.obstaclesAtlas.findRegion("barrier"));
    }

    @Override
    public void onCollision(Player player) {
        if (destroyed) return;

        if (player.isSprinting()) {
            destroyed = true;
            player.resetSprint();
            player.slowDown(1f);
        } else {
            player.kill();
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
