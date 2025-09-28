package race.config;

import data.horse.HorseData;
import data.race.EquippedHorseService;

public class GameConfigFactory {

    private static final int DEFAULT_LANES = 3;
    private static final int RACE_LENGTH = 4000;
    private static final int TRAINING_LENGTH = 300000;
    private static final float DEFAULT_SPEED = 4f;
    private static final int DEFAULT_VISIBLE_COLS = 10;
    private static final float TRAINING_POINTS_MULTIPLIER = 0.0001f;
    private static final float SPRINT_LEVEL_MULTIPLIER = 0.1f;
    private static final float DEFAULT_SPRINT = 2f;
    private static final float DEFAULT_SPRINT_COOLDOWN = 10f;

    private final EquippedHorseService horseService;

    public GameConfigFactory(EquippedHorseService horseService) {
        this.horseService = horseService;
    }

    public BaseGameConfig createMultiplayerRaceConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, RACE_LENGTH, true);
    }

    public BaseGameConfig createSingleplayerRaceConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, RACE_LENGTH, false);
    }

    public BaseGameConfig createTrainingConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, TRAINING_LENGTH, false);
    }

    private BaseGameConfig createConfigWithHorse(HorseData horseData, int length, boolean playerImmortal) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        float sprintDuration = calculateSprintDuration(horseData);
        float sprintCooldown = calculateSprintCooldown(horseData);
        return new BaseGameConfig(
                DEFAULT_LANES,
                length,
                DEFAULT_SPEED,
                horseData.getHorseType(),
                DEFAULT_VISIBLE_COLS,
                cooldownReduction,
                horseData.getLevel(),
                sprintDuration,
                sprintCooldown,
                playerImmortal
        );
    }

    private float calculateCooldownReduction(HorseData horseData) {
        return horseData.getTrainingPoints() * TRAINING_POINTS_MULTIPLIER;
    }

    private float calculateSprintDuration(HorseData horseData) {
        float levelBonus = horseData.getLevel() * SPRINT_LEVEL_MULTIPLIER;
        return DEFAULT_SPRINT * (1f + levelBonus);
    }

    private float calculateSprintCooldown(HorseData horseData) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        return DEFAULT_SPRINT_COOLDOWN * (1f - cooldownReduction);
    }
}
