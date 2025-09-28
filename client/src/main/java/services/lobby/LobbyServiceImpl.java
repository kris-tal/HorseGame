// java
package services.lobby;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import data.lobby.LobbyCreationResponse;
import services.managers.ConnectionManager;
import services.managers.ConnectionManagerPort;
import services.managers.SessionManagerPort;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LobbyServiceImpl implements LobbyService {
    private final SessionManagerPort sessionManager;
    private final ConnectionManagerPort connectionManager;
    private final Gson gson = new Gson();

    private String currentLobby;
    private boolean isLobbyCreator = false;

    public LobbyServiceImpl(SessionManagerPort sessionManager, ConnectionManagerPort connectionManager) {
        this.sessionManager = sessionManager;
        this.connectionManager = connectionManager;
    }

    @Override
    public String createLobby() {
        HttpRequest request = sessionManager.getProtectedRequestsService()
                .ProtectedPost("/lobby", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = sessionManager.getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String lobbyCode = gson.fromJson(response.body(), LobbyCreationResponse.class).getCode();
                isLobbyCreator = true;
                return lobbyCode;
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    @Override
    public boolean joinLobby(String room) {
        currentLobby = room;
        return connectionManager.connectToRoom(room, sessionManager.getToken());
    }

    @Override
    public void startLobby(String room) {
        if (currentLobby == null) return;
        if (!isLobbyCreator) return;

        HttpRequest request = sessionManager.getProtectedRequestsService()
                .ProtectedPost("/lobby/" + room + "/start", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = sessionManager.getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gdx.app.log("Lobby", "Started room " + room);
            } else {
                Gdx.app.log("Lobby", "Failed to start room " + room);
            }
        } catch (IOException | InterruptedException e) {
            Gdx.app.log("Lobby", "Failed to start room " + room);
        }
    }

    @Override
    public void updatePosition(int progress) {
        connectionManager.emitProgressUpdate(
                sessionManager.getToken(),
                progress
        );
    }

    @Override
    public void closeConnection() {
        connectionManager.closeConnection();
        currentLobby = null;
        isLobbyCreator = false;
    }
}
