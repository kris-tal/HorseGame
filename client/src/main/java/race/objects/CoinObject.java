package race.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import race.MapObject;
import race.Player;
import services.managers.ResourceManager;

public class CoinObject extends MapObject {
    boolean taken = false;

    public CoinObject() {
        super(ResourceManager.iconsAtlas.findRegion("coin_icon"));
    }

    @Override
    public void onCollision(Player player) {
        if (!taken) {
            taken = true;
            player.addCoins(1);
        }
    }

    public boolean isTaken() {
        return taken;
    }
}