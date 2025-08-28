package race.objects;

import com.badlogic.gdx.graphics.Texture;
import race.MapObject;
import race.Player;

public class CoinObject extends MapObject {
    boolean taken = false;

    public CoinObject(Texture texture) {
        super(texture);
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