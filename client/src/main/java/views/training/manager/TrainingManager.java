package views.training.manager;

import core.HorseGame;
import race.input.InputHandler;
import race.map.MapGenerator;
import services.training.TrainingService;
import services.training.TrainingServiceImpl;
import services.horse.HorseService;
import services.horse.HorseServiceImpl;
import training.contract.TrainingPresenter;
import views.training.factory.TrainingScreenFactory;
import views.training.screen.TrainingScreen;

public class TrainingManager {
    private final TrainingService trainingService;
    private final HorseService horseService;
    private final TrainingScreenFactory screenFactory;
    private TrainingPresenter currentPresenter;

    public TrainingManager(MapGenerator mapGenerator, InputHandler inputHandler) {
        this.trainingService = new TrainingServiceImpl();
        this.horseService = new HorseServiceImpl();
        this.screenFactory = new TrainingScreenFactory(
            mapGenerator, inputHandler, trainingService, horseService);
    }

    public TrainingScreen createTrainingScreen(HorseGame game) {
        TrainingScreen screen = screenFactory.createDefaultTrainingScreen(game);
        currentPresenter = screen.getPresenter();
        return screen;
    }
}