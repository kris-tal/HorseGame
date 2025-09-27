package views.training.factory;

import core.HorseGame;
import race.config.BaseGameConfig;
import race.config.GameConfigFactory;
import race.factory.GameFactory;
import race.input.InputHandler;
import race.map.MapGenerator;
import services.horse.HorseService;
import services.training.TrainingService;
import training.contract.TrainingPresenter;
import views.training.presenter.TrainingPresenterFactory;
import views.training.screen.TrainingScreen;
import services.managers.SessionManagerPort;
import data.race.EquippedHorseService;

public class TrainingScreenFactory {

    private final MapGenerator mapGenerator;
    private final InputHandler inputHandler;
    private final TrainingService trainingService;
    private final HorseService horseService;

    public TrainingScreenFactory(MapGenerator mapGenerator, InputHandler inputHandler,
                                 TrainingService trainingService, HorseService horseService) {
        this.mapGenerator = mapGenerator;
        this.inputHandler = inputHandler;
        this.trainingService = trainingService;
        this.horseService = horseService;
    }

    public TrainingScreen createDefaultTrainingScreen(HorseGame game, SessionManagerPort sessionManager, EquippedHorseService equippedHorseService) {
        BaseGameConfig config = GameConfigFactory.createTrainingConfigWithEquippedHorse();
        return createTrainingScreen(game, config, sessionManager, equippedHorseService);
    }

    public TrainingScreen createTrainingScreen(HorseGame game, BaseGameConfig config, SessionManagerPort sessionManager, EquippedHorseService equippedHorseService) {
        GameFactory gameFactory = new GameFactory(mapGenerator, inputHandler, config);
        TrainingPresenter presenter = TrainingPresenterFactory.createTrainingPresenter(
                gameFactory, config, trainingService, sessionManager, equippedHorseService
        );
        return new TrainingScreen(game, presenter, trainingService, horseService, sessionManager, equippedHorseService);
    }
}