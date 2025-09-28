package race.mode.training.map;

import race.MapObject;
import race.objects.*;
import race.map.MapObjectGenerationStrategy;

import java.util.Random;

public class TrainingMapObjectGenerationStrategy implements MapObjectGenerationStrategy {
    @Override
    public MapObject generateObject(int lane, int col, Random positionRandom) {
        double r = positionRandom.nextDouble();

        if (r < 0.01) { // DeathStone
            return new DeathStoneObject();
        } else if (r < 0.05) { // Puddle
            return new PuddleObject();
        } else if (r < 0.09) { // Bush
            return new BushObject();
        } else if (r < 0.1) { // ElectricBarrier
            return new ElectricBarrierObject();
        }
        else if (r < 0.13) { // Coin
            return new CoinObject();
        }
        
        return new EmptyObject();
    }
}
