package race.objects;

import race.MapObject;
import race.Player;

public class EmptyObject extends MapObject {
    public EmptyObject() {
        super(null); // no texture
    }

    @Override
    public void onCollision(Player player) {}
}