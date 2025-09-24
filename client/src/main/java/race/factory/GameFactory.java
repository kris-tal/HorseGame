package race.factory;

import data.race.GameMap;
import race.config.BaseGameConfig;
import race.input.InputHandler;
import race.map.MapGenerator;

public class GameFactory extends BaseGameFactory {

    public GameFactory(MapGenerator mapGenerator, InputHandler inputHandler, BaseGameConfig config,
                       PlayerFactory playerFactory, HorseActorFactory horseActorFactory, SystemsFactory systemsFactory) {
        super(mapGenerator, inputHandler, config, playerFactory, horseActorFactory, systemsFactory);
    }

    public GameFactory(MapGenerator mapGenerator, InputHandler inputHandler, BaseGameConfig config) {
        super(mapGenerator, inputHandler, config, new DefaultPlayerFactory(), new DefaultHorseActorFactory(), new DefaultSystemsFactory());
    }

    @Override
    protected GameMap generateMap() {
        return mapGenerator.generateMap(config.getLanes(), config.getLength());
    }

    public static GameWorldFactoryBuilder builder() {
        return new GameFactoryBuilder();
    }
}
