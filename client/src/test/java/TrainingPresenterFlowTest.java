import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import race.RaceWorld;
import race.config.GameConfigFactory;
import race.config.BaseGameConfig;
import animations.BaseHorseActor;
import race.camera.CameraSystem;
import race.collision.CollisionSystem;
import race.coordination.GameLoop;
import race.factory.*;
import race.input.InputHandler;
import race.map.MapGenerator;
import services.horse.HorseService;
import services.training.TrainingService;
import training.contract.TrainingPresenter;
import views.training.presenter.TrainingPresenterFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingPresenterFlowTest {

    private MapGenerator mapGenerator;
    private InputHandler inputHandler;
    private TrainingService trainingService;
    private HorseService horseService;

    @BeforeEach
    void setup() {
        mapGenerator = mock(MapGenerator.class);
        inputHandler = mock(InputHandler.class);
        trainingService = mock(TrainingService.class);
        horseService = mock(HorseService.class);
    }

    @Test
    void presenter_initializes_and_updates() {
        BaseGameConfig config = GameConfigFactory.createTrainingConfigWithCooldownReduction(0f);
        SystemsFactory testSystems = new SystemsFactory() {
            @Override public CameraSystem createCameraSystem(data.race.GameMap map, int visibleCols) { return mock(CameraSystem.class); }
            @Override public race.rendering.RendererSystem createRendererSystem(CameraSystem cameraSystem) { return mock(race.rendering.RendererSystem.class); }
            @Override public CollisionSystem createCollisionSystem() { return mock(CollisionSystem.class); }
            @Override public GameLoop createGameLoop(race.input.InputHandler ih, CameraSystem cs, CollisionSystem c, race.rendering.RendererSystem r) { return mock(GameLoop.class); }
            @Override public race.state.GameStateManager createGameStateManager() { return mock(race.state.GameStateManager.class); }
        };
        HorseActorFactory testHorse = player -> mock(BaseHorseActor.class);

        GameFactory gf = new GameFactory(mapGenerator, inputHandler, config, new DefaultPlayerFactory(), testHorse, testSystems);

        TrainingPresenter presenter = TrainingPresenterFactory.createTrainingPresenter(
                gf,
                config,
                trainingService
        );

        presenter.init();
        presenter.update(0.016f);

        assertNotNull(presenter);
    }
}


