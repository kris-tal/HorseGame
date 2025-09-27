package services.training;

import data.training.TrainingData;
import data.training.TrainingResponse;

public interface TrainingService {
    TrainingResponse performTraining(TrainingData data);

    TrainingResponse sendTrainingData(TrainingData data);
}
