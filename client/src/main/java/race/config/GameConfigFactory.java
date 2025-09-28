package race.config;

import data.horse.HorseData;
import data.race.EquippedHorseService;

public class GameConfigFactory {

    private final EquippedHorseService horseService;
    private final GameConfig gameConfig;

    public GameConfigFactory(EquippedHorseService horseService) {
        this.horseService = horseService;
        this.gameConfig = GameConfigManager.getInstance().getConfig();
    }

    public BaseGameConfig createMultiplayerRaceConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, gameConfig.getGameConfigData().getRaceLength(), true);
    }

    public BaseGameConfig createSingleplayerRaceConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, gameConfig.getGameConfigData().getRaceLength(), false);
    }

    public BaseGameConfig createTrainingConfig() {
        HorseData horse = horseService.getEquippedHorse();
        return createConfigWithHorse(horse, gameConfig.getGameConfigData().getTrainingLength(), false);
    }

    private BaseGameConfig createConfigWithHorse(HorseData horseData, int length, boolean playerImmortal) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        float sprintDuration = calculateSprintDuration(horseData);
        float sprintCooldown = calculateSprintCooldown(horseData);
        return new BaseGameConfig(
                gameConfig.getGameConfigData().getDefaultLanes(),
                length,
                gameConfig.getGameConfigData().getDefaultSpeed(),
                horseData.getHorseType(),
                gameConfig.getGameConfigData().getDefaultVisibleCols(),
                cooldownReduction,
                horseData.getLevel(),
                sprintDuration,
                sprintCooldown,
                playerImmortal
        );
    }

    private float calculateCooldownReduction(HorseData horseData) {
        return horseData.getTrainingPoints() * gameConfig.getGameConfigData().getTrainingPointsMultiplier();
    }

    private float calculateSprintDuration(HorseData horseData) {
        float levelBonus = horseData.getLevel() * gameConfig.getGameConfigData().getSprintLevelMultiplier();
        return gameConfig.getGameConfigData().getDefaultSprint() * (1f + levelBonus);
    }

    private float calculateSprintCooldown(HorseData horseData) {
        float cooldownReduction = calculateCooldownReduction(horseData);
        return gameConfig.getGameConfigData().getDefaultSprintCooldown() * (1f - cooldownReduction);
    }
}
