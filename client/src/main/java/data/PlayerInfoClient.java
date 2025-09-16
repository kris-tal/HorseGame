package data;

public class PlayerInfoClient {
    private final String id;
    private final String username;
    private final String role;
    private int progress;
    private boolean connected;

    public PlayerInfoClient(String id, String username, String role, int progress, boolean connected) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.progress = progress;
        this.connected = connected;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public int getProgress() { return progress; }
    public boolean isConnected() { return connected; }

    @Override
    public String toString() {
        return username + " (" + role + ") - progress: " + progress + "% - connected: " + connected;
    }
}
