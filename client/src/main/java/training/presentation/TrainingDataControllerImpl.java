package training.presentation;

import data.training.TrainingData;
import data.training.TrainingResponse;
import data.horse.HorseData;
import data.race.EquippedHorseService;
import services.training.TrainingService;
import services.horse.HorseService;
import race.strategy.training.TrainingPointsCalculator;
import training.contract.TrainingDataController;


public class TrainingDataControllerImpl implements TrainingDataController {
    private final TrainingService trainingService;
    private final HorseService horseService;
    private final TrainingPointsCalculator pointsCalculator;
    
    private int trainingPoints = 0;
    private int longestRunDistance = 0;
    private int pointsEarnedInThisSession = 0;
    private int lastDistanceCheckpoint = 0;

    public TrainingDataControllerImpl(TrainingService trainingService, HorseService horseService, TrainingPointsCalculator pointsCalculator) {
        this.trainingService = trainingService;
        this.horseService = horseService;
        this.pointsCalculator = pointsCalculator;
    }

    @Override
    public int calculatePointsForDistance(int distanceKm) {
        if (distanceKm > lastDistanceCheckpoint) {
            int totalPointsEarned = 0;
            for (int km = lastDistanceCheckpoint + 1; km <= distanceKm; km++) {
                int pointsForThisKm = pointsCalculator.calculatePointsForDistance(km);
                totalPointsEarned += pointsForThisKm;
            }
            pointsEarnedInThisSession += totalPointsEarned;
            lastDistanceCheckpoint = distanceKm;
            return totalPointsEarned;
        }
        return 0;
    }

    @Override
    public int getTotalPointsEarned() {
        return trainingPoints + pointsEarnedInThisSession;
    }

    public int getSessionPointsEarned() {
        return pointsEarnedInThisSession;
    }

    public int getDatabaseTrainingPoints() {
        return trainingPoints;
    }

    @Override
    public int getLongestRunDistance() {
        return longestRunDistance;
    }

    @Override
    public TrainingResponse saveTrainingData(TrainingData trainingData) {
        try {
            trainingPoints += pointsEarnedInThisSession;

            if (trainingData.distance > longestRunDistance) {
                longestRunDistance = trainingData.distance;
            }
            TrainingResponse response = trainingService.sendTrainingData(trainingData);

            resetSession();
            
            return response;
        } catch (Exception e) {
            return new TrainingResponse("{\"success\": false, \"message\": \"Failed to save training data: " + e.getMessage() + "\"}");
        }
    }

    @Override
    public void loadHorseData() {
        try {
            HorseData horse = EquippedHorseService.getInstance().getEquippedHorse();
            if (horse == null) {
                horse = horseService.getUserHorses().getFirst();
            }
            
            if (horse != null) {
                trainingPoints = horse.getTrainingPoints();
                longestRunDistance = horse.getLongestRunDistance();
                resetSession();
            }
        } catch (Exception e) {
        }
    }
    
    @Override
    public void resetSession() {
        pointsEarnedInThisSession = 0;
        lastDistanceCheckpoint = 0;
    }
}