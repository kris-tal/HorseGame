import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.HorseGame;

public class DesktopLauncher {
    public static void main(String[] args) {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Horse Game");
        config.setForegroundFPS(60);
        
        Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        
        /*config.setWindowedMode(1470, 956);*/config.setFullscreenMode(displayMode);
        config.setResizable(false);
        
        new Lwjgl3Application(new HorseGame(), config);
    }
}
