package training.contract;

import race.RaceWorld;

public interface TrainingPresenter {
    void init();
    void update(float delta);
    
    void handleTrainingFailure();
    void saveTrainingDataBeforeExit(int horseId);
    void completeTraining(int horseId);
    
    boolean isRaceFinished();
    boolean isPlayerDead();
    int getDistance();
    RaceWorld getWorld();
    
    int getTrainingPoints();
    float getSprintCooldownReduction();
    String getDisplayLongestRun();
    
    String getLongestRunDisplayText();
    String getTrainingPointsDisplayText();
    int getCoins();
}
