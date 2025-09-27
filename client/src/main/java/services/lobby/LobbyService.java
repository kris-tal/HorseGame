package services.lobby;

public interface LobbyService {
    String createLobby();
    boolean joinLobby(String room);
    void startLobby(String room);
    void updatePosition(int progress);
    void closeConnection();
}
