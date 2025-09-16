package services.lobby;

import com.badlogic.gdx.Gdx;
import data.PlayerInfoClient;

import java.util.HashMap;

public class LobbyTracker {
    private HashMap<String, PlayerInfoClient> players = new HashMap<>();

    /* Handle the data sent by server */
    public void updateFromServer(String jsonData) {
        Gdx.app.log("socket", jsonData);

    }

    public void markPlayerLeft(String playerId) {
        PlayerInfoClient p = players.get(playerId);
        if (p != null) {
            p.setConnected(false);
        }
    }

    public void printLobbyStatus() {
        System.out.println("Current Lobby Players:");
        for (PlayerInfoClient p : players.values()) {
            System.out.println(p);
        }
    }
}
