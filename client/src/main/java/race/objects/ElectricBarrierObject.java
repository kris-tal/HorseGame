package race.objects;

import com.badlogic.gdx.graphics.Texture;
import race.MapObject;
import race.Player;

public class ElectricBarrierObject extends MapObject {

    private boolean destroyed = false;

    public ElectricBarrierObject(Texture texture) {
        super(texture);
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
