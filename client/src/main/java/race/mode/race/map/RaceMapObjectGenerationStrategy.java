package race.mode.race.map;

import race.MapObject;
import race.objects.*;
import race.map.MapObjectGenerationStrategy;

import java.util.Random;

public class RaceMapObjectGenerationStrategy implements MapObjectGenerationStrategy {
    @Override
    public MapObject generateObject(int lane, int col, Random random) {
        double r = random.nextDouble();
        if (r < 0.01) return new DeathStoneObject(); // Zmniejszone z 0.03 do 0.01
        if (r < 0.07) return new CoinObject();
        if (r < 0.10) return new PuddleObject();
        if (r < 0.15) return new BushObject();
        if (r < 0.16) return new ElectricBarrierObject(); // Zmniejszone z 0.17 do 0.16
        return new EmptyObject();
    }
}
