package training.presentation;

import training.contract.TrainingUIController;
import training.contract.TrainingDataController;

public class TrainingUIControllerImpl implements TrainingUIController {
    private final TrainingDataController dataController;
    
    public TrainingUIControllerImpl(TrainingDataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String getLongestRunDisplayText() {
        return "Longest Run: " + dataController.getLongestRunDistance() + "m";
    }

    @Override
    public String getTrainingPointsDisplayText() {
        return "Training Points: " + dataController.getTotalPointsEarned() + "/10000";
    }

    @Override
    public void updateUI() {
    }

    @Override
    public void showTrainingCompletion(boolean success, int points, int distance) {
    }

    @Override
    public void showTrainingFailure(int distance) {
    }
}
