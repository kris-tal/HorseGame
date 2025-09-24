package race.map;

import java.util.Random;
import race.mode.race.map.RaceMapObjectGenerationStrategy;

public class RandomMapGenerator extends AbstractMapGenerator {
    public RandomMapGenerator() {
        super(new RaceMapObjectGenerationStrategy(), new Random());
    }
}
