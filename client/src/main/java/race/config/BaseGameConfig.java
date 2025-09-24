package race.config;

import data.horse.HorseType;

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
        return new BaseGameConfig(3, 300, 4f, HorseType.BORING_HORSE, 10, 0f); // 3 km
    }

    public static BaseGameConfig createTrainingConfig() {
        return new BaseGameConfig(3, 300000, 4f, HorseType.BORING_HORSE, 10, 0f); // 100x większa niż wyścig
    }

    public static BaseGameConfig createRaceConfigWithCooldownReduction(float sprintCooldownReduction) {
        return new BaseGameConfig(3, 300, 4f, HorseType.BORING_HORSE, 10, sprintCooldownReduction); // 3 km
    }

    public static BaseGameConfig createTrainingConfigWithCooldownReduction(float sprintCooldownReduction) {
        return new BaseGameConfig(3, 300000, 4f, HorseType.BORING_HORSE, 10, sprintCooldownReduction); // 100x większa niż wyścig
    }
}


