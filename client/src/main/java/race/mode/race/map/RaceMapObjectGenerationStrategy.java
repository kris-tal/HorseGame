package race.mode.race.map;

import race.MapObject;
import race.objects.*;
import race.map.MapObjectGenerationStrategy;

import java.util.Random;

public class RaceMapObjectGenerationStrategy implements MapObjectGenerationStrategy {
    @Override
    public MapObject generateObject(int lane, int col, Random random) {
        if (col == 301) {
            return new MetaObject();
        }
        
        if (col > 301) {
            return new EmptyObject();
        }
        
        double r = random.nextDouble();
        if(r < 0.04) return new DeathStoneObject();
        if (r < 0.08) return new CoinObject();
        if (r < 0.12) return new PuddleObject();
        if (r < 0.16) return new BushObject();
        if (r < 0.20) return new ElectricBarrierObject();
        return new EmptyObject();
    }
}
