package race.objects;

import com.badlogic.gdx.graphics.Texture;
import race.MapObject;
import race.Player;

public class DeathStoneObject extends MapObject {
    public DeathStoneObject(Texture texture) {
        super(texture);
    }

    @Override
    public void onCollision(Player player) {
        player.kill();
    }
}