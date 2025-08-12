package horsegame.data;

import java.util.List;

public class HorseType {
    public String id;
    public String displayName;
    public List<String> giftPreferences;    //tu bedzie id
    //texture

    public HorseType(String id, String displayName, List<String> giftPreferences) {
        this.id = id;
        this.displayName = displayName;
        this.giftPreferences = giftPreferences;
    }

}
