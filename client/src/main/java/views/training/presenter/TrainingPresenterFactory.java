package views.training.presenter;

import race.factory.GameFactory;
import race.config.BaseGameConfig;
import race.strategy.training.TrainingPointsCalculator;
import race.strategy.training.ArithmeticProgressionPointsCalculator;
import services.training.TrainingService;
import services.horse.HorseService;
import services.horse.HorseServiceImpl;
import training.presentation.TrainingGameControllerImpl;
import training.presentation.TrainingDataControllerImpl;
import training.presentation.TrainingUIControllerImpl;
import training.contract.TrainingGameController;
import training.contract.TrainingDataController;
import training.contract.TrainingUIController;
import training.contract.TrainingPresenter;

public class TrainingPresenterFactory {
    
    public static TrainingPresenter createTrainingPresenter(
            GameFactory gameFactory, 
            BaseGameConfig trainingConfig, 
            TrainingService trainingService) {

        TrainingPointsCalculator pointsCalculator = new ArithmeticProgressionPointsCalculator();
        HorseService horseService = new HorseServiceImpl();
        
        TrainingGameController gameController = new TrainingGameControllerImpl(
            gameFactory, trainingConfig);
        
        TrainingDataController dataController = new TrainingDataControllerImpl(
            trainingService, horseService, pointsCalculator);
        
        TrainingUIController uiController = new TrainingUIControllerImpl(dataController);
        
        return new TrainingPresenterImpl(gameController, dataController, uiController);
    }
}