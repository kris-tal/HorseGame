package race.config;

import data.horse.HorseType;
import data.horse.HorseData;
import data.race.EquippedHorseService;

public class BaseGameConfig {
    protected final int lanes;
    protected final int length;
    protected final float speed;
    protected final HorseType horseType;
    protected final int visibleCols;
    protected final float sprintCooldownReduction;

    public BaseGameConfig(int lanes, int length, float speed, HorseType horseType, int visibleCols, float sprintCooldownReduction) {
        this.lanes = lanes;
        this.length = length;
        this.speed = speed;
        this.horseType = horseType;
        this.visibleCols = visibleCols;
        this.sprintCooldownReduction = sprintCooldownReduction;
    }

    public int getLanes() { return lanes; }
    public int getLength() { return length; }
    public float getSpeed() { return speed; }
    public HorseType getHorseType() { return horseType; }
    public int getVisibleCols() { return visibleCols; }
    public float getSprintCooldownReduction() { return sprintCooldownReduction; }

    public static BaseGameConfig createRaceConfig() {
        return new BaseGameConfig(3, 400, 4f, HorseType.BORING_HORSE, 10, 0f);
    }

    public static BaseGameConfig createTrainingConfig() {
        return new BaseGameConfig(3, 300000, 4f, HorseType.BORING_HORSE, 10, 0f);
    }

    public static BaseGameConfig createRaceConfigWithCooldownReduction(float sprintCooldownReduction) {
        return new BaseGameConfig(3, 400, 4f, HorseType.BORING_HORSE, 10, sprintCooldownReduction);
    }

    public static BaseGameConfig createTrainingConfigWithCooldownReduction(float sprintCooldownReduction) {
        return new BaseGameConfig(3, 300000, 4f, HorseType.BORING_HORSE, 10, sprintCooldownReduction);
    }

    public static BaseGameConfig createRaceConfigWithEquippedHorse() {
        HorseData equippedHorse = EquippedHorseService.getInstance().getEquippedHorse();
        if (equippedHorse != null) {
            float cooldownReduction = equippedHorse.getTrainingPoints() * 0.0001f;
            return new BaseGameConfig(3, 400, 4f, equippedHorse.getHorseType(), 10, cooldownReduction);
        }
        return createRaceConfig();
    }

    public static BaseGameConfig createTrainingConfigWithEquippedHorse() {
        HorseData equippedHorse = EquippedHorseService.getInstance().getEquippedHorse();
        if (equippedHorse != null) {
            float cooldownReduction = equippedHorse.getTrainingPoints() * 0.0001f;
            return new BaseGameConfig(3, 300000, 4f, equippedHorse.getHorseType(), 10, cooldownReduction);
        }
        return createTrainingConfig();
    }

    public static BaseGameConfig createRaceConfigWithHorse(HorseData horseData) {
        if (horseData == null) {
            return createRaceConfig();
        }
        float cooldownReduction = horseData.getTrainingPoints() * 0.0001f;
        return new BaseGameConfig(3, 400, 4f, horseData.getHorseType(), 10, cooldownReduction);
    }

    public static BaseGameConfig createTrainingConfigWithHorse(HorseData horseData) {
        if (horseData == null) {
            return createTrainingConfig();
        }
        float cooldownReduction = horseData.getTrainingPoints() * 0.0001f;
        return new BaseGameConfig(3, 300000, 4f, horseData.getHorseType(), 10, cooldownReduction);
    }
}


