package race.config;

import data.horse.HorseData;
import data.horse.HorseType;
import data.race.EquippedHorseService;

public class GameConfigFactory {

    private static final int DEFAULT_LANES = 3;
    private static final int RACE_LENGTH = 400;
    private static final int TRAINING_LENGTH = 300000;
    private static final float DEFAULT_SPEED = 4f;
    private static final int DEFAULT_VISIBLE_COLS = 10;
    private static final float TRAINING_POINTS_MULTIPLIER = 0.0001f;

    public static BaseGameConfig createDefaultRaceConfig() {
        return new BaseGameConfig(
            DEFAULT_LANES, 
            RACE_LENGTH, 
            DEFAULT_SPEED, 
            HorseType.BORING_HORSE, 
            DEFAULT_VISIBLE_COLS, 
            0f,
            0
        );
    }

    public static BaseGameConfig createDefaultTrainingConfig() {
        return new BaseGameConfig(
            DEFAULT_LANES, 
            TRAINING_LENGTH, 
            DEFAULT_SPEED, 
            HorseType.BORING_HORSE, 
            DEFAULT_VISIBLE_COLS, 
            0f,
            0
        );
    }

    public static BaseGameConfig createRaceConfigWithCooldownReduction(float sprintCooldownReduction) {
        return new BaseGameConfig(
            DEFAULT_LANES, 
            RACE_LENGTH, 
            DEFAULT_SPEED, 
            HorseType.BORING_HORSE, 
            DEFAULT_VISIBLE_COLS, 
            sprintCooldownReduction,
            0
        );
    }

    public static BaseGameConfig createTrainingConfigWithCooldownReduction(float sprintCooldownReduction) {
        return new BaseGameConfig(
            DEFAULT_LANES, 
            TRAINING_LENGTH, 
            DEFAULT_SPEED, 
            HorseType.BORING_HORSE, 
            DEFAULT_VISIBLE_COLS, 
            sprintCooldownReduction,
            0
        );
    }

    public static BaseGameConfig createRaceConfigWithEquippedHorse() {
        HorseData equippedHorse = EquippedHorseService.getInstance().getEquippedHorse();
        if (equippedHorse != null) {
            return createConfigWithHorse(equippedHorse, RACE_LENGTH);
        }
        return createDefaultRaceConfig();
    }

    public static BaseGameConfig createTrainingConfigWithEquippedHorse() {
        HorseData equippedHorse = EquippedHorseService.getInstance().getEquippedHorse();
        if (equippedHorse != null) {
            return createConfigWithHorse(equippedHorse, TRAINING_LENGTH);
        }
        return createDefaultTrainingConfig();
    }

    public static BaseGameConfig createTrainingConfigWithHorse(HorseData horseData) {
        if (horseData == null) {
            return createDefaultTrainingConfig();
        }
        return createConfigWithHorse(horseData, TRAINING_LENGTH);
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
