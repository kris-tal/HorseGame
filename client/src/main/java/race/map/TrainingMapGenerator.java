package race.map;

import race.mode.training.map.TrainingMapObjectGenerationStrategy;

import java.util.Random;

public class TrainingMapGenerator extends AbstractMapGenerator {
    public TrainingMapGenerator() {
        super(new TrainingMapObjectGenerationStrategy(), new Random());
    }
}





