package services.managers;

import data.lobby.LobbyInfo;
import data.lobby.PlayerInfoClient;
import data.race.PlayerProgressResponse;
import services.lobby.LobbyService;
import services.lobby.LobbyServiceImpl;
import services.profile.ProfileServiceImpl;
import views.race.menu.WaitingLobbyPanel;

import java.util.HashMap;

public class LobbyManager {
    private static LobbyManager instance;
    private final LobbyService service;
    private LobbyInfo currentLobby;
    private LobbyStateListener stateListener;
    private LobbyConnectionListener connectionListener;

    /* Interface responsible for callback from server responses,
     Callback is designed to communicate with WaitingLobbyPanel*/
    public interface LobbyStateListener {
        void onLobbyInitialized();
        void onPlayersUpdated();
        void onLobbyStarted();
        void onLobbyClosed();
    }

    /* Interface responsible for callback from server responses,
     Callback is designed to communicate with JoinLobbyPanel*/
    public interface LobbyConnectionListener {
        void onLobbyJoined(boolean success);
    }

    private LobbyManager() {
        service = new LobbyServiceImpl();
    }

    public static synchronized LobbyManager getInstance() {
        if (instance == null) {
            instance = new LobbyManager();
        }
        return instance;
    }

    public void createLobby() {
        if (currentLobby != null) {
            return;
        }
        service.closeConnection();
        
        String code = service.createLobby();
        if (code != null) {
            currentLobby = new LobbyInfo(code);
            service.joinLobby(code);
        }
    }

    public void joinLobby(String lobbyCode) {
        if (currentLobby != null) {
            service.closeConnection();
        }

        // Create lobby info immediately (optimistic)
        currentLobby = new LobbyInfo(lobbyCode);

        boolean success = service.joinLobby(lobbyCode);
        if (!success) {
            // If immediate failure, clear the lobby
            currentLobby = null;
            if (connectionListener != null) {
                connectionListener.onLobbyJoined(false);
            }
        }
        // If success, wait for async callback to confirm
    }

    public void startLobby() {
        if (currentLobby == null) {
            return;
        }

        service.startLobby();
    }

    public LobbyInfo getCurrentLobby() {
        return currentLobby;
    }

    public void leaveLobby() {
        if (currentLobby != null) {
            currentLobby = null;
        }
        service.closeConnection();
    }

    public void updatePlayerList(HashMap<String, PlayerInfoClient> newPlayers) {
        if (currentLobby != null) {
            currentLobby.setPlayers(newPlayers);
            if (stateListener != null) {
                stateListener.onPlayersUpdated();
            }
        }
    }
    public void updateProgress(PlayerProgressResponse progress) {
        for (PlayerProgressResponse.PlayerProgress data : progress.getDistances()) {
            currentLobby.getPlayer(data.getUsername()).setProgress(data.getProgress());
        }
    }

    public void onLobbyStarted() {
        if (stateListener != null) {
            stateListener.onLobbyStarted();
        }
    }

    public void onLobbyClosed() {
        currentLobby = null;
        if (stateListener != null) {
            stateListener.onLobbyClosed();
        }
    }

    public void setStateListener(LobbyStateListener listener) {
        this.stateListener = listener;
    }

    public void setConnectionListener(LobbyConnectionListener listener) {
        this.connectionListener = listener;
    }

    public boolean isCurrentUserCreator() {
        if (currentLobby == null) {
            return false;
        }

        String username = new ProfileServiceImpl().getUsername();
        if (username == null) return false;

        PlayerInfoClient player = currentLobby.getPlayer(username);
        if (player == null) return false;

        return player.isCreator();
    }

    public void updateStartButtonVisibility() {
        if (stateListener != null && stateListener instanceof WaitingLobbyPanel) {
            ((WaitingLobbyPanel) stateListener).updateStartButtonVisibility();
        }
    }

    public void onLobbyJoined(boolean success) {
        if (connectionListener != null) {
            connectionListener.onLobbyJoined(success);
        }

        if (success) {
            if (stateListener != null) {
                stateListener.onLobbyInitialized();
            }
        } else {
            currentLobby = null;
        }
    }

}