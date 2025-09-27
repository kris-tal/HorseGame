// java
package services.managers;

import data.lobby.LobbyInfo;
import data.lobby.PlayerInfoClient;
import data.race.PlayerProgressResponse;
import services.listeners.LobbyListener;
import services.lobby.LobbyService;
import services.lobby.LobbyServiceImpl;
import services.profile.ProfileServiceImpl;

import java.util.HashMap;

public class LobbyManager implements LobbyListener {
    private final SessionManagerPort sessionManager;
    private final ConnectionManager connectionManager;
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

    public LobbyManager(SessionManagerPort sessionManager, ConnectionManager connectionManager) {
        this.sessionManager = sessionManager;
        this.service = new LobbyServiceImpl(sessionManager);
        this.connectionManager = connectionManager;
        this.connectionManager.setLobbyListener(this);
    }

    public void createLobby() {
        if (currentLobby != null) {
            return;
        }
        connectionManager.closeConnection();
        String code = service.createLobby();
        if (code != null) {
            currentLobby = new LobbyInfo(code);
            service.joinLobby(code);
        }
    }

    public void joinLobby(String lobbyCode) {
        if (currentLobby != null) {
            connectionManager.closeConnection();
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
        service.startLobby(currentLobby.getLobbyCode());
    }

    public LobbyInfo getCurrentLobby() {
        return currentLobby;
    }

    public void leaveLobby() {
        if (currentLobby != null) {
            currentLobby = null;
        }
        connectionManager.closeConnection();
    }

    public void updateProgress(PlayerProgressResponse progress) {
        if (currentLobby == null || progress == null || progress.getDistances() == null) return;
        for (PlayerProgressResponse.PlayerProgress data : progress.getDistances()) {
            PlayerInfoClient player = currentLobby.getPlayer(data.getUsername());
            if (player != null) {
                player.setProgress(data.getProgress());
            }
        }
        if (stateListener != null) {
            stateListener.onPlayersUpdated();
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

        String username = new ProfileServiceImpl(sessionManager).getUsername();
        if (username == null) return false;

        PlayerInfoClient player = currentLobby.getPlayer(username);
        if (player == null) return false;

        return player.isCreator();
    }

    // Implementation of LobbyListener interface
    @Override
    public void onPlayerListChanged(HashMap<String, PlayerInfoClient> newPlayers) {
        if (currentLobby != null) {
            currentLobby.setPlayers(newPlayers);
            if (stateListener != null) {
                stateListener.onPlayersUpdated();
            }
        }
    }

    @Override
    public void onLobbyStarted() {
        if (stateListener != null) {
            stateListener.onLobbyStarted();
        }
    }

    @Override
    public void onLobbyClosed() {
        currentLobby = null;
        if (stateListener != null) {
            stateListener.onLobbyClosed();
        }
    }

    @Override
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

    @Override
    public void onLobbyCreated(String lobbyCode) {
        // Handle lobby creation confirmation if needed
    }

    @Override
    public void onLobbyInitialized() {
        if (stateListener != null) {
            stateListener.onLobbyInitialized();
        }
    }
}