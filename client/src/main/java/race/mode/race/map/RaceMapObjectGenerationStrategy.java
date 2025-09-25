package race.mode.race.map;

import race.MapObject;
import race.objects.*;
import race.map.MapObjectGenerationStrategy;

import java.util.Random;

public class RaceMapObjectGenerationStrategy implements MapObjectGenerationStrategy {
    @Override
    public MapObject generateObject(int lane, int col, Random random) {
        double r = random.nextDouble();
        if (r < 0.05) return new DeathStoneObject(); 
        if (r < 0.1) return new CoinObject();
        if (r < 0.15) return new PuddleObject();
        if (r < 0.2) return new BushObject();
        if (r < 0.25) return new ElectricBarrierObject(); 
        return new EmptyObject();
    }
}
