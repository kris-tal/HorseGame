package horsegame.registry;

import horsegame.data.ItemType;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private static Map<String, ItemType> itemTypes = new HashMap<String, ItemType>();

    public static void loadFromJSON(ItemType type) {

    }

    public static ItemType get(String id) {
        return itemTypes.get(id);
    }

    public static Map<String, ItemType> getAll() {
        return itemTypes;
    }
}
