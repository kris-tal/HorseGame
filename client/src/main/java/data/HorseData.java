package data;

import com.google.gson.Gson;

// Represent one horse
public class HorseData {
    private int id;
    private final String name;
    private final String race;
    private final int speed;
    private final int stamina;
    private final int level;

    public HorseData(String json) {
        HorseData temp = new Gson().fromJson(json, HorseData.class);
        this.id = temp.id;
        this.name = temp.name;
        this.race = temp.race;
        this.speed = temp.speed;
        this.stamina = temp.stamina;
        this.level = temp.level;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public String getRace() { return race; }

    public int getSpeed() { return speed; }

    public int getStamina() { return stamina; }

    public int getLevel() { return level; }

    @Override
    public String toString() {
        return String.format("<Horse %s (%s) lvl=%d>", name, race, level);
    }
}

