package race.config;

import data.horse.HorseData;

public class RaceConfig {
    private final int lanes;
    private final int length;
    private final float speed;
    private final HorseData horseData;
    private final int visibleCols;

    public RaceConfig(int lanes, int length, float speed, HorseData horseData, int visibleCols) {
        this.lanes = lanes;
        this.length = length;
        this.speed = speed;
        this.horseData = horseData;
        this.visibleCols = visibleCols;
    }

    public int getLanes() {
        return lanes;
    }

    public int getLength() {
        return length;
    }

    public float getSpeed() {
        return speed;
    }

    public HorseData getHorseData() {
        return horseData;
    }

    public int getVisibleCols() {
        return visibleCols;
    }

    public static RaceConfig createDefault(HorseData horseData) {
        return new RaceConfig(3, 300, 4f, horseData, 10);
    }

    //tutaj tworzymy nie deafaultowe ustawienia wyścigu
    public static RaceConfig createCustom(int lanes, int length, float speed, HorseData horseData) {
        return new RaceConfig(lanes, length, speed, horseData, 10);
    }
}
