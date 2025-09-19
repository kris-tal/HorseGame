package race;

import race.objects.*;

import java.util.function.Supplier;

enum MapObjectType {
    DEATHSTONE(0.1, DeathStoneObject::new),
    COIN(0.02, CoinObject::new),
    PUDDLE(0.04, PuddleObject::new),
    BUSH(0.08, BushObject::new),
    ELECTRIC_BARRIER(0.06, ElectricBarrierObject::new);

    final double weight;
    final Supplier<MapObject> factory;

    MapObjectType(double weight, Supplier<MapObject> factory) {
        this.weight = weight;
        this.factory = factory;
    }

    static MapObject random(int col) {
        if (col <= 2) return new EmptyObject();
        double r = Math.random();
        double cumulative = 0.0;
        for (MapObjectType type : values()) {
            cumulative += type.weight;
            if (r < cumulative) return type.factory.get();
        }
        return new EmptyObject();
    }
}
