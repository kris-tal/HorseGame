package data.horse;

import animations.HorseAnimationAssembler;
import animations.HorseAnimationSet;
import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public enum HorseType {
    BORING_HORSE(
            1,
            "Boring Horse",
            new HorsePalette(
                    new Color(0.5f, 0.5f, 0.5f, 1f),   // body
                    new Color(0.4f, 0.4f, 0.4f, 1f),   // mane
                    new Color(0.3f, 0.3f, 0.3f, 1f)    // tail
            ),
            new Color(0.5f, 0.5f, 0.5f, 1f)        // legacy "main" color
    ),
    NORMAL_HORSE(
            2,
            "Normal Horse (Average)",
            new HorsePalette(
                    new Color(0.7f, 0.6f, 0.5f, 1f),   // body (brown)
                    new Color(0.5f, 0.4f, 0.3f, 1f),   // mane (darker brown)
                    new Color(0.8f, 0.7f, 0.6f, 1f)    // tail (lighter brown)
            ),
            new Color(0.7f, 0.6f, 0.5f, 1f)
    ),
    TURING_MACHINE(
            3,
            "Turing Machine",
            new HorsePalette(
                    new Color(0.2f, 0.4f, 0.8f, 1f),   // body (machine blue)
                    new Color(0.1f, 0.2f, 0.5f, 1f),   // mane (dark navy)
                    new Color(0.5f, 0.6f, 0.9f, 1f)    // tail (light code blue)
            ),
            new Color(0.2f, 0.4f, 0.8f, 1f)
    ),
    RAGE(
            4,
            "Rage",
            new HorsePalette(
                    new Color(0.8f, 0.1f, 0.1f, 1f),   // body (red)
                    new Color(0.5f, 0f, 0f, 1f),       // mane (dark red)
                    new Color(1f, 0.2f, 0.2f, 1f)      // tail (fiery red)
            ),
            new Color(0.8f, 0.1f, 0.1f, 1f)
    ),
    PINK_PRINCESS_BALLERINA(
            5,
            "Pink Princess Ballerina",
            new HorsePalette(
                    new Color(1f, 0.6f, 0.8f, 1f),     // body (soft pink)
                    new Color(1f, 0.8f, 0.9f, 1f),     // mane (pastel pink)
                    new Color(1f, 0.4f, 0.6f, 1f)      // tail (bold pink)
            ),
            new Color(1f, 0.6f, 0.8f, 1f)
    ),
    CTRL_VICTORY(
            6,
            "Ctrl+V(ictory)",
            new HorsePalette(
                    new Color(0.3f, 0.9f, 0.4f, 1f),   // body (bright green)
                    new Color(0.1f, 0.6f, 0.2f, 1f),   // mane (forest green)
                    new Color(0.6f, 1f, 0.6f, 1f)      // tail (light green)
            ),
            new Color(0.3f, 0.9f, 0.4f, 1f)
    ),
    PROBABILITY_BOOK(
            7,
            "Probability & Computing 2nd Edition",
            new HorsePalette(
                    new Color(0.2f, 0.2f, 0.2f, 1f),   // body (dark gray)
                    new Color(0.1f, 0.1f, 0.1f, 1f),   // mane (black)
                    new Color(0.3f, 0.3f, 0.3f, 1f)    // tail (medium gray)
            ),
            new Color(0.2f, 0.2f, 0.2f, 1f)
    ),
    LANA_DEL_REY(
            8,
            "Lana del Rey",
            new HorsePalette(
                    new Color(0.4f, 0.6f, 1f, 1f),     // body (dreamy blue)
                    new Color(0.2f, 0.4f, 0.8f, 1f),   // mane (deep sky)
                    new Color(0.6f, 0.8f, 1f, 1f)      // tail (light pastel blue)
            ),
            new Color(0.4f, 0.6f, 1f, 1f)
    ),
    SILLY(
            9,
            ":P",
            new HorsePalette(
                    new Color(1f, 1f, 0.3f, 1f),       // body (yellow)
                    new Color(1f, 0.9f, 0.1f, 1f),     // mane (golden)
                    new Color(1f, 1f, 0.5f, 1f)        // tail (pale yellow)
            ),
            new Color(1f, 1f, 0.3f, 1f)
    ),
    SAW(
            10,
            "Saw I (2004)",
            new HorsePalette(
                    new Color(0.6f, 0f, 0f, 1f),       // body (dark blood red)
                    new Color(0.3f, 0f, 0f, 1f),       // mane (almost black red)
                    new Color(0.8f, 0.1f, 0.1f, 1f)    // tail (bright blood)
            ),
            new Color(0.6f, 0f, 0f, 1f)
    ),
    PIOTR(
            11,
            "Piotr",
            new HorsePalette(
                    new Color(0.8f, 0.7f, 0.2f, 1f),   // body (gold)
                    new Color(0.6f, 0.5f, 0.1f, 1f),   // mane (bronze)
                    new Color(1f, 0.9f, 0.4f, 1f)      // tail (bright yellow-gold)
            ),
            new Color(0.8f, 0.7f, 0.2f, 1f)
    ),
    HORSE(
            12,
            "Horse",
            new HorsePalette(
                    new Color(0.9f, 0.9f, 0.9f, 1f),   // body (white)
                    new Color(0.7f, 0.7f, 0.7f, 1f),   // mane (gray)
                    new Color(1f, 1f, 1f, 1f)          // tail (pure white)
            ),
            new Color(0.9f, 0.9f, 0.9f, 1f)
    );


    private final int id;
    private final String name;
    private final HorsePalette palette;
    private final Color color;
    private HorseAnimationSet cachedAnimations;

    HorseType(int id, String name, HorsePalette palette, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.palette = palette;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HorsePalette getPalette() {
        return palette;
    }

    public Color getColor() {
        return color;
    }

    public HorseAnimationSet getAnimationSet() {
        if (cachedAnimations == null) cachedAnimations = HorseAnimationAssembler.assemble(this);
        return cachedAnimations;
    }

    public String getAtlasKey() {
        return name().toLowerCase();
    }

    public String getAtlasKey(String action) {
        return name().toLowerCase() + "_" + action;
    }

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
