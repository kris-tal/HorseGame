package data;

import com.google.gson.Gson;

import java.util.Date;

public class ProfileData {
    private final String username;
    private final String email;
    private final int money;
    private final int prestige;
    private final Date created_at;

    public ProfileData(String json) {
        ProfileData temp = new Gson().fromJson(json, ProfileData.class);
        this.username = temp.username;
        this.email = temp.email;
        this.money = temp.money;
        this.prestige = temp.prestige;
        this.created_at = temp.created_at;
    }
}

