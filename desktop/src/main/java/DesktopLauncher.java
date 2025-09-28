import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.HorseGame;
import data.horse.HorseTypeRegistry;
import services.managers.ConnectionManager;
import services.managers.ConnectionManagerPort;
import services.managers.SessionManager;
import services.managers.SessionManagerPort;
import services.media.AudioService;
import services.media.ResourceManagerAudioService;

public class DesktopLauncher {
    public static void main(String[] args) {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Horse Game");
        config.setForegroundFPS(60);
        Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        config.setFullscreenMode(displayMode);
        config.setResizable(false);

        //composition root
        AudioService audio = new ResourceManagerAudioService();
        SessionManagerPort sessionManager = new SessionManager();
        ConnectionManagerPort connectionManager = new ConnectionManager();
        HorseTypeRegistry.loadDefault();

        HorseGame game = new HorseGame(sessionManager, connectionManager, audio);
        new Lwjgl3Application(game, config);
    }
}