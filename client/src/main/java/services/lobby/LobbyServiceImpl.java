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
        System.out.println("LobbyServiceImpl: createLobby() called");
        System.out.println("LobbyServiceImpl: token = " + SessionManager.getInstance().getToken());
        System.out.println("LobbyServiceImpl: isLoggedIn = " + SessionManager.getInstance().isLoggedIn());
        
        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService()
                .ProtectedPost("/lobby", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = SessionManager.getInstance()
                    .getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("LobbyServiceImpl: response status = " + response.statusCode());
            System.out.println("LobbyServiceImpl: response body = " + response.body());

            if (response.statusCode() == 200) {
                String lobbyCode = gson.fromJson(response.body(), LobbyCreationResponse.class).getCode();
                isLobbyCreator = true;
                System.out.println("LobbyServiceImpl: created lobby with code = " + lobbyCode);
                return lobbyCode;
            } else {
                System.out.println("LobbyServiceImpl: failed to create lobby, status = " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("LobbyServiceImpl: error creating lobby: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean joinLobby(String room) {
        try {
            System.out.println("LobbyServiceImpl: joinLobby() called with room: " + room);
            System.out.println("LobbyServiceImpl: token = " + SessionManager.getInstance().getToken());
            
            // Zamknij poprzednie połączenie jeśli istnieje
            if (sessionSocket != null) {
                System.out.println("LobbyServiceImpl: Closing previous socket");
                sessionSocket.close();
            }
            
            IO.Options options = new IO.Options();
            options.auth = new HashMap<>();
            options.auth.put("token", SessionManager.getInstance().getToken());
            options.auth.put("room", room);

            currentLobby = room;
            sessionSocket = IO.socket(new URI(BASE_URL), options);
            setupSocketEvents();
            sessionSocket.open();
            sessionSocket.connect();
            
            System.out.println("LobbyServiceImpl: Socket connection initiated");

            // Return true immediately - actual connection status handled via events
            return true; // FIXED: Was returning false always

        } catch (Exception e) {
            System.out.println("LobbyServiceImpl: Error joining lobby: " + e.getMessage());
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
        System.out.println("LobbyServiceImpl: Setting up socket events");
        
        sessionSocket.on("player_joined", args -> {
            System.out.println("LobbyServiceImpl: player_joined event received");
            if (args.length > 1) {
                String data = args[1].toString();
                System.out.println("LobbyServiceImpl: player_joined data: " + data);
                lobbyTracker.updateFromServer(data);
            } else {
                String data = args[0].toString();
                System.out.println("LobbyServiceImpl: player_joined data: " + data);
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

        sessionSocket.on("room_started", args -> {
            System.out.println("LobbyServiceImpl: room_started event received");
            LobbyManager.getInstance().onLobbyStarted();
        });

        sessionSocket.on("room_closed", args -> {
            LobbyManager.getInstance().onLobbyClosed();
        });

        sessionSocket.on("connect", args -> {
            System.out.println("LobbyServiceImpl: Socket connected successfully");
            try {
                currentUserUsername = new ProfileServiceImpl().getUserData().getUsername();
                connected = true;
                System.out.println("LobbyServiceImpl: Connected as user: " + currentUserUsername);

                Gdx.app.postRunnable(() -> {
                    LobbyManager.getInstance().onLobbyJoined(true);
                    // Update start button visibility after username is set
                    LobbyManager.getInstance().updateStartButtonVisibility();
                });
            } catch (Exception e) {
                System.out.println("LobbyServiceImpl: Error getting user data: " + e.getMessage());
                Gdx.app.postRunnable(() -> {
                    LobbyManager.getInstance().onLobbyJoined(false);
                });
            }
        });

        sessionSocket.on("disconnect", args -> {
            System.out.println("LobbyServiceImpl: Socket disconnected");
            connected = false;
            LobbyManager.getInstance().onLobbyClosed();
        });

        sessionSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            System.out.println("LobbyServiceImpl: Socket connection error");
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
            // Usuń wszystkie event handlery przed zamknięciem
            sessionSocket.off();
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
        System.out.println("LobbyServiceImpl: getCurrentUserUsername() - currentUserUsername = " + currentUserUsername);
        return currentUserUsername;
    }
}