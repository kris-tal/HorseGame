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
    private static final float SPRINT_LEVEL_MULTIPLIER = 0.1f;
    private static final float DEFAULT_SPRINT = 2f;

    private final EquippedHorseService horseService;

    public GameConfigFactory(EquippedHorseService horseService) {
        this.horseService = horseService;
    }

    public BaseGameConfig createRaceConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, RACE_LENGTH);
    }

    public BaseGameConfig createTrainingConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, TRAINING_LENGTH);
    }

    private BaseGameConfig createConfigWithHorse(HorseData horseData, int length) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        float sprintDuration = calculateSprintDuration(horseData);
        return new BaseGameConfig(
                DEFAULT_LANES,
                length,
                DEFAULT_SPEED,
                horseData.getHorseType(),
                DEFAULT_VISIBLE_COLS,
                cooldownReduction,
                horseData.getLevel(),
                sprintDuration
        );
    }

    private float calculateCooldownReduction(HorseData horseData) {
        return horseData.getTrainingPoints() * TRAINING_POINTS_MULTIPLIER;
    }

    private float calculateSprintDuration(HorseData horseData) {
        float levelBonus = horseData.getLevel() * SPRINT_LEVEL_MULTIPLIER;
        return DEFAULT_SPRINT * (1f + levelBonus);
    }
}
