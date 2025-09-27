package services.managers;

import data.lobby.LobbyInfo;
import data.lobby.PlayerInfoClient;
import data.race.PlayerProgressResponse;
import race.Player;
import services.listeners.GameListener;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*  Instantiating this class will set it as a listener to ConnectionManager
*  You need two arguments, sessionManager and connectionManager
*/
public class GameManager implements GameListener {
    interface MockGameListener {
        void onProgressUpdate();
        void onGameWon();
        void onLobbyClose();
    }

    private MockGameListener mockListener;
    private final SessionManager sessionManager;
    private final ConnectionManager connectionManager;
    private HashMap<String, PlayerInfoClient> currentPlayers;
    private boolean gameInProgress = false;
    private String winner;

    public GameManager(SessionManager sessionManager, ConnectionManager connectionManager, LobbyInfo lobbyInfo) {
        this.sessionManager = sessionManager;
        this.connectionManager = connectionManager;
        this.connectionManager.setGameListener(this);
        this.currentPlayers = lobbyInfo.getPlayers();
    }

    public void setCurrentPlayers(HashMap<String, PlayerInfoClient> players) {
        currentPlayers = players;
    }

    public void startGame() {
        gameInProgress = true;
        winner = null;
    }

    public void endGame() {
        gameInProgress = false;
        currentPlayers = null;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public String getWinner() {
        return winner;
    }

    public HashMap<String, PlayerInfoClient> getCurrentPlayers() {
        return currentPlayers;
    }
    public void updatePosition(int newPosition) {
        connectionManager.emitProgressUpdate(sessionManager.getToken(), newPosition);
    }
    @Override
    public void onProgressUpdate(List<PlayerProgressResponse.PlayerProgress> playersProgress) {
        for (PlayerProgressResponse.PlayerProgress p : playersProgress) {
            if(currentPlayers.containsKey(p.getUsername())) {
                currentPlayers.get(p.getUsername()).setProgress(p.getProgress());
            }
        }

        if(mockListener != null) {
            mockListener.onProgressUpdate();
        }
    }

    @Override
    public void onGameWon(String winnerUsername) {
        this.winner = winnerUsername;
        gameInProgress = false;

        if(mockListener != null) {
            mockListener.onGameWon();
        }
        System.out.println("Game won by: " + winnerUsername);
    }

    @Override
    public void onGameClosed() {
        gameInProgress = false;
        currentPlayers = null;
        winner = null;
        if(mockListener != null) {
            mockListener.onLobbyClose();
        }
        System.out.println("Game was closed/disconnected");
    }

    /*public void setMockListener(MockGameListener listener) {
        this.mockListener = listener;
    }*/
}