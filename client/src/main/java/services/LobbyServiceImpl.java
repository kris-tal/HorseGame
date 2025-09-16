package services;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import data.LobbyCreationResponse;
import data.ShopResponse;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
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
    public void joinLobby(String room) {
        try {
            IO.Options options = new IO.Options();
            options.auth = new HashMap<>();
            options.auth.put("token", SessionManager.getInstance().getToken()) ;
            options.auth.put("room", room);

            sessionSocket = IO.socket(new URI(BASE_URL), options);
            // Handle events
            sessionSocket.on(Socket.EVENT_CONNECT, args1 -> {
                System.out.println("Connected to server!");
            });

            sessionSocket.on("lobby_join", args1 -> {
                Gdx.app.log("Lobby","Joined: " + room);
            });

            sessionSocket.on(Socket.EVENT_DISCONNECT, args1 -> {
                Gdx.app.log("Lobby", "Disconnected!");
            });

            sessionSocket.connect();
            currentLobby = room;
        } catch (Exception exception) {
            Gdx.app.log("Lobby", exception.getMessage());
        }
    }

    @Override
    public void startLobby() {




    }
}
