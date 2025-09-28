import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.HorseGame;
import data.race.EquippedHorseService;
import race.input.KeyboardInputHandler;
import race.map.RandomMapGenerator;
import services.managers.SessionManager;
import services.managers.SessionManagerPort;
import services.media.AudioService;
import services.media.ResourceManagerAudioService;
import views.login.LoginScreen;
import views.register.RegisterScreen;
import views.ranch.RanchScreen;
import views.training.manager.TrainingManager;

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

        HorseGame game = new HorseGame(sessionManager, audio);
        new Lwjgl3Application(game, config);
    }
}