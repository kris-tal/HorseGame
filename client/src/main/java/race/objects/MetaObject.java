package race.objects;

import race.MapObject;
import race.Player;
import services.managers.ResourceManager;

public class MetaObject extends MapObject {
    public MetaObject() {
        super(ResourceManager.obstaclesAtlas.findRegion("barrier"));
    }

    @Override
    public void onCollision(Player player) {
        player.success();
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public boolean isFatal() {
        return false; 
    }
}