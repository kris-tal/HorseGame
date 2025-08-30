package data;
import java.util.HashMap;
import java.util.Map;

public enum HorseType {
    BORING_HORSE(1, "Boring Horse"),
    NORMAL_HORSE(2, "Normal Horse (Average)"),
    TURING_MACHINE(3, "Turing Machine"),
    RAGE(4, "Rage"),
    PINK_PRINCESS_BALLERINA(5, "Pink Princess Ballerina"),
    CTRL_VICTORY(6, "Ctrl+V(ictory)"),
    PROBABILITY_BOOK(7, "Probability & Computing 2nd Edition"),
    LANA_DEL_REY(8, "Lana del Rey"),
    SILLY(9, ":P"),
    SAW(10, "Saw I (2004)"),
    PIOTR(11, "Piotr"),
    HORSE(12, "Horse");

    private final int id;
    private final String displayName;

    HorseType(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() { return id; }
    public String getDisplayName() { return displayName; }

    public String getAtlasKey() {
        return name().toLowerCase();
    }

    public String getAtlasKey(String action) {
        return name().toLowerCase() + "_" + action;
    }

    // Lookup table for id -> HorseType
    private static final Map<Integer, HorseType> BY_ID = new HashMap<>();
    static {
        for (HorseType horse : values()) {
            BY_ID.put(horse.id, horse);
        }
    }

    public static HorseType fromId(int id) {
        return BY_ID.get(id);
    }

}
