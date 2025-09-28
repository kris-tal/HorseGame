package race.map;

import java.util.Random;
import race.objects.*;

public class TrainingMapGenerator extends AbstractMapGenerator {
    public TrainingMapGenerator() {
        super(
                new ConfigurableMapObjectGenerationStrategy(
                        createTrainingConfig(),
                        null
                ),
                new Random()
        );
    }

    private static MapObjectProbabilityConfig createTrainingConfig() {
        MapObjectProbabilityConfig config = new MapObjectProbabilityConfig();
        config.addProbability(0.01, DeathStoneObject.class);
        config.addProbability(0.05, PuddleObject.class);
        config.addProbability(0.09, BushObject.class);
        config.addProbability(0.10, ElectricBarrierObject.class);
        config.addProbability(0.13, CoinObject.class);
        return config;
    }
}
