package services.listeners;

import data.lobby.PlayerInfoClient;
import java.util.HashMap;

public interface LobbyListener {
    void onPlayerListChanged(HashMap<String, PlayerInfoClient> newPlayers);
    void onLobbyStarted();
    void onLobbyClosed();
    void onLobbyJoined(boolean success);
    void onLobbyCreated(String lobbyCode);
    void onLobbyInitialized();
}