package horsegame.registry;

import horsegame.data.HorseData;

import java.util.HashMap;
import java.util.Map;

public class HorseRegistry {
    private static Map<String, HorseData> horseTypes = new HashMap<>();

    public void loadFromJSON(String json) {     //idk if im gonna use path or what

    }

    public static HorseData get(String id) {
        return horseTypes.get(id);
    }

    public static Map<String, HorseData> getAll() {
        return horseTypes;
    }
}
