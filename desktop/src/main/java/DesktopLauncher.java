import view.OurGame;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Horse Game");
        // config.setWindowedMode(800, 720);
        config.setWindowedMode(720, 720);
        new Lwjgl3Application(new OurGame(), config);
    }
}
