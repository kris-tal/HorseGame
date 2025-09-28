package race.config;

import data.horse.HorseData;

import data.horse.HorseType;
import data.horse.HorseTypeRegistry;
import data.race.EquippedHorseService;

public class GameConfigFactory {

    private static final int DEFAULT_LANES = 3;
    private static final int RACE_LENGTH = 400;
    private static final int TRAINING_LENGTH = 300000;
    private static final float DEFAULT_SPEED = 4f;
    private static final int DEFAULT_VISIBLE_COLS = 10;
    private static final float TRAINING_POINTS_MULTIPLIER = 0.0001f;

    //hardcoded default horse id (BORING_HORSE)

    private static HorseType defaultHorseType() {
        return HorseTypeRegistry.getByKey("boring_horse");
    }

    public static BaseGameConfig createDefaultRaceConfig() {
        return new BaseGameConfig(
                DEFAULT_LANES,
                RACE_LENGTH,
                DEFAULT_SPEED,
                defaultHorseType(),
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
                defaultHorseType(),
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
                defaultHorseType(),
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
                defaultHorseType(),
                DEFAULT_VISIBLE_COLS,
                sprintCooldownReduction,
                0
        );
    }

    public static BaseGameConfig createRaceConfigWithEquippedHorse() {
        return createDefaultRaceConfig();
    }

    public static BaseGameConfig createTrainingConfigWithEquippedHorse() {
        return createDefaultTrainingConfig();
    }

    public static BaseGameConfig createRaceConfigWithEquippedHorse(EquippedHorseService equippedHorseService) {
        HorseData equippedHorse = safeGetEquippedHorse(equippedHorseService);
        if (equippedHorse != null) {
            return createConfigWithHorse(equippedHorse, RACE_LENGTH);
        }
        return createDefaultRaceConfig();
    }

    public static BaseGameConfig createTrainingConfigWithEquippedHorse(EquippedHorseService equippedHorseService) {
        HorseData equippedHorse = safeGetEquippedHorse(equippedHorseService);
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

    private static HorseData safeGetEquippedHorse(EquippedHorseService service) {
        if (service == null) return null;
        try {
            return service.getEquippedHorse();
        } catch (Throwable ignored) {
            return null;
        }
    }
}