package data.race;

import data.horse.HorseData;
import services.horse.HorseService;
import services.horse.HorseServiceImpl;
import services.managers.SessionManagerPort;

import java.util.List;
import java.util.Objects;

public class EquippedHorseService {
    private final HorseService horseService;
    private HorseData equippedHorse;

    public EquippedHorseService(SessionManagerPort sessionManager) {
        this(new HorseServiceImpl(Objects.requireNonNull(sessionManager, "sessionManager")));
    }

    public EquippedHorseService(HorseService horseService) {
        this.horseService = Objects.requireNonNull(horseService, "horseService");
        List<HorseData> horses = this.horseService.getUserHorses();
        if (horses != null && !horses.isEmpty()) {
            this.equippedHorse = horses.get(0);
        }
    }

    public HorseData getEquippedHorse() {
        return equippedHorse;
    }

    public void equipHorse(HorseData horse) {
        this.equippedHorse = horse;
    }

    public boolean isEquipped(HorseData horseData) {
        return equippedHorse == horseData;
    }
}