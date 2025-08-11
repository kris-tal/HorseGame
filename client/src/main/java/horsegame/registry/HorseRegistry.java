package horsegame.registry;

import horsegame.data.HorseType;
import horsegame.game.Horse;

import java.util.HashMap;
import java.util.Map;

public class HorseRegistry {
    private static Map<String, HorseType> horseTypes = new HashMap<>();

    public void loadFromJSON(String json) {     //idk if im gonna use path or what

    }

    public static HorseType get(String id) {
        return horseTypes.get(id);
    }

    public static Map<String, HorseType> getAll() {
        return horseTypes;
    }
}
