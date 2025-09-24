import data.race.GameMap;
import org.junit.jupiter.api.Test;
import race.MapObject;
import animations.BaseHorseActor;
import race.camera.CameraSystem;
import race.collision.CollisionSystem;
import race.coordination.GameLoop;
import race.factory.GameFactory;
import race.factory.GameWorldFactory;
import race.factory.HorseActorFactory;
import race.factory.PlayerFactory;
import race.factory.SystemsFactory;
import race.input.InputHandler;
import race.map.MapGenerator;
import race.config.BaseGameConfig;
import race.RaceWorld;
import race.objects.EmptyObject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RaceWorldCreationTest {

    @Test
    void builder_buildsFactory_andCreatesWorld() {
        MapGenerator mapGenerator = mock(MapGenerator.class);
        InputHandler inputHandler = mock(InputHandler.class);

        int lanes = 3;
        int length = 20;
        MapObject[][] data = new MapObject[lanes][length];
        for (int i = 0; i < lanes; i++) {
            for (int j = 0; j < length; j++) {
                data[i][j] = new EmptyObject();
            }
        }
        when(mapGenerator.generateMap(anyInt(), anyInt())).thenReturn(new GameMap(lanes, length, data));

        BaseGameConfig config = BaseGameConfig.createRaceConfigWithCooldownReduction(0f);

        SystemsFactory testSystems = new SystemsFactory() {
            @Override public CameraSystem createCameraSystem(GameMap map, int visibleCols) { return mock(CameraSystem.class); }
            @Override public race.rendering.RendererSystem createRendererSystem(CameraSystem cameraSystem) { return mock(race.rendering.RendererSystem.class); }
            @Override public CollisionSystem createCollisionSystem() { return mock(CollisionSystem.class); }
            @Override public GameLoop createGameLoop(InputHandler ih, CameraSystem cs, CollisionSystem c, race.rendering.RendererSystem r) { return mock(GameLoop.class); }
            @Override public race.state.GameStateManager createGameStateManager() { return mock(race.state.GameStateManager.class); }
        };

        HorseActorFactory testHorse = player -> mock(BaseHorseActor.class);

        GameWorldFactory factory = GameFactory.builder()
                .withMapGenerator(mapGenerator)
                .withInputHandler(inputHandler)
                .withConfig(config)
                .withSystemsFactory(testSystems)
                .withHorseActorFactory(testHorse)
                .build();

        RaceWorld world = factory.createWorld();

        assertNotNull(world);
        assertNotNull(world.getMap());
        assertNotNull(world.getPlayer());
        assertTrue(world.getMap().getLanes() > 0);
    }
}


