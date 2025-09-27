package services.lobby;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.lobby.PlayerInfoClient;
import data.lobby.PlayerListResponse;
import services.managers.LobbyManager;

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
                LobbyManager.getInstance().updatePlayerList(map);
            }
        } catch (Exception e) {
            System.out.println("LobbyTracker: Error parsing jsonData: " + jsonData + ", error: " + e.getMessage());
        }
    }
}