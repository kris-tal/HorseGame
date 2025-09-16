package race.objects;

import com.badlogic.gdx.graphics.Texture;
import race.MapObject;
import race.Player;

public class BushObject extends MapObject {

    public BushObject(Texture texture) {
        super(texture);
    }

    @Override
    public void onCollision(Player player) {
        player.limitVisionByMeters(10f);
        player.resetSprint();
    }
}
