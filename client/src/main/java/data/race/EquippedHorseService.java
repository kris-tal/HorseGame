package data.race;

import data.horse.HorseData;
import services.horse.HorseServiceImpl;

import java.util.List;

public class EquippedHorseService {
    private static EquippedHorseService instance;
    private HorseData equippedHorse;

    private EquippedHorseService() {
        List<HorseData> horses = new HorseServiceImpl().getUserHorses();
        if (horses != null && !horses.isEmpty()) {
            this.equippedHorse = horses.get(0); // auto-equip first horse
        }
    }

    public static EquippedHorseService getInstance() {
        if (instance == null) {
            instance = new EquippedHorseService();
        }
        return instance;
    }

    public HorseData getEquippedHorse() {
        return equippedHorse;
    }

    public void equipHorse(HorseData horse) {
        this.equippedHorse = horse;
    }
}
