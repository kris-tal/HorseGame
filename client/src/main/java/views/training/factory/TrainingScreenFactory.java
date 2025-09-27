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

/**
 * Factory for creating training screens following SOLID principles.
 * Implements Single Responsibility Principle - only responsible for creating training screens.
 * Implements Dependency Inversion Principle - depends on abstractions (services, factories).
 */
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
    
    /**
     * Creates a training screen with default configuration (equipped horse)
     */
    public TrainingScreen createDefaultTrainingScreen(HorseGame game) {
        BaseGameConfig config = GameConfigFactory.createTrainingConfigWithEquippedHorse();
        return createTrainingScreen(game, config);
    }
    
    /**
     * Creates a training screen with custom configuration
     */
    public TrainingScreen createTrainingScreen(HorseGame game, BaseGameConfig config) {
        GameFactory gameFactory = new GameFactory(mapGenerator, inputHandler, config);
        TrainingPresenter presenter = TrainingPresenterFactory.createTrainingPresenter(
            gameFactory, config, trainingService);
        return new TrainingScreen(game, presenter, trainingService, horseService);
    }
    
    /**
     * Creates a training screen with specific horse data
     */
    public TrainingScreen createTrainingScreenWithHorse(HorseGame game, data.horse.HorseData horseData) {
        BaseGameConfig config = GameConfigFactory.createTrainingConfigWithHorse(horseData);
        return createTrainingScreen(game, config);
    }
}
