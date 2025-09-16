package services.lobby;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import data.LobbyCreationResponse;
import io.socket.client.IO;
import io.socket.client.Socket;
import services.managers.SessionManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static services.auth.AuthServiceImpl.BASE_URL;

public class LobbyServiceImpl implements LobbyService {
    String currentLobby;
    Socket sessionSocket;
    Gson gson;
    public LobbyServiceImpl(){
        gson = new Gson();
    }
    @Override
    public String createLobby() {
        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService()
                .ProtectedPost("/lobby", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = SessionManager.getInstance()
                    .getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), LobbyCreationResponse.class).getCode();
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean joinLobby(String room) {
        try {
            IO.Options options = new IO.Options();
            options.auth = new HashMap<>();
            options.auth.put("token", SessionManager.getInstance().getToken());
            options.auth.put("room", room);

            sessionSocket = IO.socket(new URI(BASE_URL), options);
            setupSocketEvents();
            sessionSocket.connect();

            currentLobby = room;
            return true;
        } catch (Exception e) {
            Gdx.app.log("Lobby", "Error joining lobby: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean startLobby() {
        if (currentLobby == null) {
            Gdx.app.log("Lobby", "No lobby to start!");
            return false;
        }

        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService()
                .ProtectedPost("/lobby/" + currentLobby + "/start", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = SessionManager.getInstance()
                    .getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gdx.app.log("Lobby", "Lobby started successfully!");
                return true;
            } else {
                Gdx.app.log("Lobby", "Failed to start lobby: " + response.body());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            Gdx.app.log("Lobby", "Error starting lobby: " + e.getMessage());
            return false;
        }
    }

    private final LobbyTracker lobbyTracker = new LobbyTracker();

    private void setupSocketEvents() {
        sessionSocket.on(Socket.EVENT_CONNECT, args -> Gdx.app.log("Lobby","Connected!"));

        sessionSocket.on("lobby_join", args -> {
            String data = args[0].toString();
            lobbyTracker.updateFromServer("[" + data + "]"); // wrap single object in array
            Gdx.app.log("Lobby","Joined: " + data);
            lobbyTracker.printLobbyStatus();
        });

        sessionSocket.on("progress_update", args -> {
            String data = args[0].toString();
            lobbyTracker.updateFromServer(data);
            Gdx.app.log("Lobby","Progress update: " + data);
            lobbyTracker.printLobbyStatus();
        });

        sessionSocket.on("player_left", args -> {
            String playerId = args[0].toString(); // assuming server sends {"id": "playerId"}
            lobbyTracker.markPlayerLeft(playerId);
            Gdx.app.log("Lobby","Player left: " + playerId);
            lobbyTracker.printLobbyStatus();
        });

        sessionSocket.on("game_won", args -> {
            Gdx.app.log("Lobby","Game won: " + args[0].toString());
        });

        sessionSocket.on(Socket.EVENT_DISCONNECT, args -> Gdx.app.log("Lobby", "Disconnected!"));
    }

    @Override
    public void updatePosition(int progress) {
        if (sessionSocket != null && sessionSocket.connected()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("token", SessionManager.getInstance().getToken());
            data.put("room", currentLobby);
            data.put("progress", progress);

            sessionSocket.emit("progress_update", gson.toJson(data));
        }
    }
}
