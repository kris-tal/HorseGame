package services.lobby;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import data.lobby.LobbyCreationResponse;
import io.socket.client.IO;
import io.socket.client.Socket;
import services.managers.LobbyManager;
import services.managers.SessionManager;
import services.profile.ProfileServiceImpl;

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
    private final LobbyTracker lobbyTracker;
    private String currentUserUsername;
    private boolean isLobbyCreator = false;
    private boolean connected = false;

    public LobbyServiceImpl() {
        gson = new Gson();
        lobbyTracker = new LobbyTracker();
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
        try {
            IO.Options options = new IO.Options();
            options.auth = new HashMap<>();
            options.auth.put("token", SessionManager.getInstance().getToken());
            options.auth.put("room", room);

            currentLobby = room;
            sessionSocket = IO.socket(new URI(BASE_URL), options);
            setupSocketEvents();
            sessionSocket.open();
            sessionSocket.connect();

            // Return true immediately - actual connection status handled via events
            return true; // FIXED: Was returning false always

        } catch (Exception e) {
            currentLobby = null;
            return false;
        }
    }

    @Override
    public boolean startLobby() {
        if (currentLobby == null) return false;

        if (!isLobbyCreator) return false;

        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService()
                .ProtectedPost("/lobby/" + currentLobby + "/start", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = SessionManager.getInstance()
                    .getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    private void setupSocketEvents() {
        sessionSocket.on("player_joined", args -> {
            if (args.length > 1) {
                String data = args[1].toString();
                lobbyTracker.updateFromServer(data);
            } else {
                String data = args[0].toString();
                lobbyTracker.updateFromServer(data);
            }
        });

        sessionSocket.on("progress_update", args -> {
            // String data = args[1].toString();
            // lobbyTracker.updateFromServer(data);
        });

        sessionSocket.on("player_left", args -> {
            if (args.length > 1) {
                String data = args[1].toString();
                lobbyTracker.updateFromServer(data);
            } else {
                String data = args[0].toString();
                lobbyTracker.updateFromServer(data);
            }
        });

        sessionSocket.on("lobby_started", args -> {
            LobbyManager.getInstance().onLobbyStarted();
        });

        sessionSocket.on("room_closed", args -> {
            LobbyManager.getInstance().onLobbyClosed();
        });

        sessionSocket.on("connect", args -> {
            try {
                currentUserUsername = new ProfileServiceImpl().getUserData().getUsername();
                connected = true;

                Gdx.app.postRunnable(() -> {
                    LobbyManager.getInstance().onLobbyJoined(true);
                });
            } catch (Exception e) {
                Gdx.app.postRunnable(() -> {
                    LobbyManager.getInstance().onLobbyJoined(false);
                });
            }
        });

        sessionSocket.on("disconnect", args -> {
            connected = false;
            LobbyManager.getInstance().onLobbyClosed();
        });

        sessionSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            connected = false;

            Gdx.app.postRunnable(() -> {
                LobbyManager.getInstance().onLobbyJoined(false);
            });
        });
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

    @Override
    public void closeConnection() {
        if (sessionSocket != null) {
            sessionSocket.close();
        }
        sessionSocket = null;
        currentLobby = null;
        currentUserUsername = null;
        isLobbyCreator = false;
        connected = false;
    }

    public boolean isLobbyCreator() {
        return isLobbyCreator;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getCurrentUserUsername() {
        return currentUserUsername;
    }
}