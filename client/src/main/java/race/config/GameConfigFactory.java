package race.config;

import data.horse.HorseData;
import data.race.EquippedHorseService;

public class GameConfigFactory {

    private static final int DEFAULT_LANES = 3;
    private static final int RACE_LENGTH = 400;
    private static final int TRAINING_LENGTH = 300000;
    private static final float DEFAULT_SPEED = 4f;
    private static final int DEFAULT_VISIBLE_COLS = 10;
    private static final float TRAINING_POINTS_MULTIPLIER = 0.0001f;


    public static BaseGameConfig createRaceConfigWithEquippedHorse(EquippedHorseService equippedHorseService) {
        HorseData equippedHorse = equippedHorseService.getEquippedHorse();
        return createConfigWithHorse(equippedHorse, RACE_LENGTH);
    }

    public static BaseGameConfig createTrainingConfigWithEquippedHorse(EquippedHorseService equippedHorseService) {
        HorseData equippedHorse = equippedHorseService.getEquippedHorse();
        return createConfigWithHorse(equippedHorse, TRAINING_LENGTH);
    }

    private static BaseGameConfig createConfigWithHorse(HorseData horseData, int length) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        return new BaseGameConfig(
                DEFAULT_LANES,
                length,
                DEFAULT_SPEED,
                horseData.getHorseType(),
                DEFAULT_VISIBLE_COLS,
                cooldownReduction,
                horseData.getLevel()
        );
    }

    private static float calculateCooldownReduction(HorseData horseData) {
        return horseData.getTrainingPoints() * TRAINING_POINTS_MULTIPLIER;
    }

}