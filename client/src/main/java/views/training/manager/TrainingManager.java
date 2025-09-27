package views.training.manager;

import race.config.BaseGameConfig;
import race.factory.GameFactory;
import race.input.InputHandler;
import race.map.MapGenerator;
import services.training.TrainingService;
import services.training.TrainingServiceImpl;
import services.horse.HorseService;
import services.horse.HorseServiceImpl;
import core.HorseGame;
import views.training.presenter.TrainingPresenterFactory;
import training.contract.TrainingPresenter;
import views.training.screen.TrainingScreen;
import data.horse.HorseData;
import java.util.List;

public class TrainingManager {
    private final MapGenerator mapGenerator;
    private final InputHandler inputHandler;
    private final TrainingService trainingService;
    private final HorseService horseService;
    private TrainingPresenter currentPresenter;

    public TrainingManager(MapGenerator mapGenerator, InputHandler inputHandler) {
        this.mapGenerator = mapGenerator;
        this.inputHandler = inputHandler;
        this.trainingService = new TrainingServiceImpl();
        this.horseService = new HorseServiceImpl();
    }

    public TrainingScreen createTrainingScreen(HorseGame game) {
        BaseGameConfig config = BaseGameConfig.createTrainingConfigWithEquippedHorse();
        GameFactory gameFactory = new GameFactory(mapGenerator, inputHandler, config);
        currentPresenter = TrainingPresenterFactory.createTrainingPresenter(gameFactory, config, trainingService);
        return new TrainingScreen(game, currentPresenter, trainingService, horseService);
    }

    public TrainingScreen createTrainingScreen(HorseGame game, BaseGameConfig config) {
        GameFactory gameFactory = new GameFactory(mapGenerator, inputHandler, config);
        currentPresenter = TrainingPresenterFactory.createTrainingPresenter(gameFactory, config, trainingService);
        return new TrainingScreen(game, currentPresenter, trainingService, horseService);
    }

    public TrainingPresenter getCurrentPresenter() {
        return currentPresenter;
    }

    public TrainingService getTrainingService() {
        return trainingService;
    }
}