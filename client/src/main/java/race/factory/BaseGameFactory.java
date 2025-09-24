package race.factory;

import race.Player;
import race.RaceWorld;
import animations.BaseHorseActor;
import race.camera.CameraSystem;
import race.collision.CollisionSystem;
import race.coordination.GameLoop;
import race.input.InputHandler;
import race.map.MapGenerator;
import race.rendering.RendererSystem;
import race.state.GameStateManager;
import data.race.GameMap;
import race.config.BaseGameConfig;
import race.strategy.training.TrainingSprintStrategy;

public abstract class BaseGameFactory implements GameWorldFactory {
    protected final MapGenerator mapGenerator;
    protected final InputHandler inputHandler;
    protected final BaseGameConfig config;
    protected final PlayerFactory playerFactory;
    protected final HorseActorFactory horseActorFactory;
    protected final SystemsFactory systemsFactory;

    protected BaseGameFactory(MapGenerator mapGenerator, InputHandler inputHandler, BaseGameConfig config,
                              PlayerFactory playerFactory, HorseActorFactory horseActorFactory, SystemsFactory systemsFactory) {
        this.mapGenerator = mapGenerator;
        this.inputHandler = inputHandler;
        this.config = config;
        this.playerFactory = playerFactory;
        this.horseActorFactory = horseActorFactory;
        this.systemsFactory = systemsFactory;
    }

    public final RaceWorld createWorld() {
        GameMap map = generateMap();

        Player player = createPlayer();

        BaseHorseActor horseActor = createHorseActor(player);

        CameraSystem cameraSystem = createCameraSystem(map);
        RendererSystem rendererSystem = createRendererSystem(cameraSystem);
        CollisionSystem collisionSystem = createCollisionSystem();
        GameLoop gameLoop = createGameLoop(cameraSystem, collisionSystem, rendererSystem);
        GameStateManager gameStateManager = createGameStateManager();

        return new RaceWorld(map, player, horseActor, gameLoop, gameStateManager);
    }

    protected abstract GameMap generateMap();

    protected Player createPlayer() { return playerFactory.create(config); }

    protected BaseHorseActor createHorseActor(Player player) { return horseActorFactory.create(player); }

    protected int getVisibleCols() {
        return config.getVisibleCols();
    }

    protected CameraSystem createCameraSystem(GameMap map) { return systemsFactory.createCameraSystem(map, getVisibleCols()); }

    protected RendererSystem createRendererSystem(CameraSystem cameraSystem) { return systemsFactory.createRendererSystem(cameraSystem); }

    protected CollisionSystem createCollisionSystem() { return systemsFactory.createCollisionSystem(); }

    protected GameLoop createGameLoop(CameraSystem cameraSystem, CollisionSystem collisionSystem, RendererSystem rendererSystem) { return systemsFactory.createGameLoop(inputHandler, cameraSystem, collisionSystem, rendererSystem); }

    protected GameStateManager createGameStateManager() { return systemsFactory.createGameStateManager(); }

    // legacy hook preserved (not used when providers injected)
    protected BaseHorseActor createHorseActorComponent(Player player) { return horseActorFactory.create(player); }
}
