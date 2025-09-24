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
        
        config.setWindowedMode(displayMode.width, displayMode.height);
        config.setDecorated(false); 
        config.setResizable(false);
        
        try {
            new Lwjgl3Application(new HorseGame(), config);
        } catch (Exception e) {
            System.err.println("Failed to start in windowed fullscreen mode: " + e.getMessage());
            var fallbackConfig = new Lwjgl3ApplicationConfiguration();
            fallbackConfig.setTitle("Horse Game");
            fallbackConfig.setForegroundFPS(60);
            fallbackConfig.setWindowedMode(1024, 768);
            fallbackConfig.setDecorated(true);
            
            try {
                new Lwjgl3Application(new HorseGame(), fallbackConfig);
            } catch (Exception fallbackException) {
                System.err.println("Fallback configuration also failed: " + fallbackException.getMessage());
                System.exit(1);
            }
        }
    }
}
