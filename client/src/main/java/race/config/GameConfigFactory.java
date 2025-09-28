package race.config;

import data.horse.HorseData;
import data.race.EquippedHorseService;

public class GameConfigFactory {

    private final EquippedHorseService horseService;

    public GameConfigFactory(EquippedHorseService horseService) {
        this.horseService = horseService;
    }

    public BaseGameConfig createMultiplayerRaceConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, GameConfigValues.getRaceLength(), true);
    }

    public BaseGameConfig createSingleplayerRaceConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, GameConfigValues.getRaceLength(), false);
    }

    public BaseGameConfig createTrainingConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, GameConfigValues.getTrainingLength(), false);
    }

    private BaseGameConfig createConfigWithHorse(HorseData horseData, int length, boolean playerImmortal) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        float sprintDuration = calculateSprintDuration(horseData);
        float sprintCooldown = calculateSprintCooldown(horseData);
        return new BaseGameConfig(
                GameConfigValues.getDefaultLanes(),
                length,
                GameConfigValues.getDefaultSpeed(),
                horseData.getHorseType(),
                GameConfigValues.getDefaultVisibleCols(),
                cooldownReduction,
                horseData.getLevel(),
                sprintDuration,
                sprintCooldown,
                playerImmortal
        );
    }

    private float calculateCooldownReduction(HorseData horseData) {
        return horseData.getTrainingPoints() * GameConfigValues.getTrainingPointsMultiplier();
    }

    private float calculateSprintDuration(HorseData horseData) {
        float levelBonus = horseData.getLevel() * GameConfigValues.getSprintLevelMultiplier();
        return GameConfigValues.getDefaultSprint() * (1f + levelBonus);
    }

    private float calculateSprintCooldown(HorseData horseData) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        return GameConfigValues.getDefaultSprintCooldown() * (1f - cooldownReduction);
    }
}
