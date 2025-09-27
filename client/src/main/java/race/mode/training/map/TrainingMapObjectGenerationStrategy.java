package race.mode.training.map;

import race.MapObject;
import race.objects.EmptyObject;
import race.objects.DeathStoneObject;
import race.objects.BushObject;
import race.objects.ElectricBarrierObject;
import race.objects.PuddleObject;
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
        } else if (r < 0.11) { // ElectricBarrier
            return new ElectricBarrierObject();
        }
        
        return new EmptyObject();
    }
}
