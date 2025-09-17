package services.horse;

import data.horse.HorseData;

import java.util.List;

public interface HorseService {
    List<HorseData> getUserHorses();
    HorseData getHorseData(int horseId);

}
