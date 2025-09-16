package race.objects;

import com.badlogic.gdx.graphics.Texture;
import race.MapObject;
import race.Player;

public class PuddleObject extends MapObject {

    public PuddleObject(Texture texture) {
        super(texture);
    }

    @Override
    public void onCollision(Player player) {
        player.slowDown();
        player.resetSprint();
    }
}
