package services.lobby;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data.lobby.PlayerInfoClient;
import data.lobby.PlayerListResponse;
import services.managers.LobbyManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class LobbyTracker {
    private final Gson gson;

    public LobbyTracker() {
        gson = new GsonBuilder()
                .registerTypeAdapter(data.lobby.PlayerStatus.class, new PlayerInfoClient.PlayerStatusDeserializer())
                .create();
    }

    public void updateFromServer(String jsonData) {
        try {
            List<PlayerInfoClient> players = gson.fromJson(jsonData, PlayerListResponse.class).getPlayers();

            if (players != null) {
                HashMap<String, PlayerInfoClient> map = new HashMap<>();
                for (PlayerInfoClient p : players) {
                    map.put(p.getUsername(), p);
                }
                System.out.println("LobbyTracker: Updating players, count: " + map.size());
                LobbyManager.getInstance().updatePlayers(map);
            } else {
                System.out.println("LobbyTracker: Players is null, jsonData: " + jsonData);
            }
        } catch (Exception e) {
            System.out.println("LobbyTracker: Error parsing jsonData: " + jsonData + ", error: " + e.getMessage());
        }
    }
}