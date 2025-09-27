package services.managers;

import io.socket.client.Socket;

public class ConnectionManager {
    private static ConnectionManager instance;
    Socket sessionSocket;
    private ConnectionManager() {

    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
}
