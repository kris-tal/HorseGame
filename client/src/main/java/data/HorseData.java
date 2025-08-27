package data;

import com.google.gson.Gson;

// Represent one horse
public class HorseData {
    private int id;
    private final String name;
    private final int level;

    public HorseData(String json) {
        HorseData temp = new Gson().fromJson(json, HorseData.class);
        this.id = temp.id;
        this.name = temp.name;
        this.level = temp.level;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public int getLevel() { return level; }

    @Override
    public String toString() {
        return String.format("<Horse %s lvl=%d>", name, level);
    }
}

