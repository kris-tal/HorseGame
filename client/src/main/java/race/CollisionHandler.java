package race;

import data.race.GameMap;
import race.objects.CoinObject;
import race.objects.EmptyObject;

public class CollisionHandler {
    public void checkCollisions(Player player, GameMap map) {
        int col = Math.round(player.getCol());

        if (col < map.getLength()) {
            int lane = player.getLane();
            MapObject obj = map.getObject(lane, col);

            obj.onCollision(player);

            if (obj instanceof CoinObject coin && coin.isTaken()) {
                map.setObject(lane, col, new EmptyObject());
            }
        }
    }
}
