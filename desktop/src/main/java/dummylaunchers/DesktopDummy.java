package dummylaunchers;

// DesktopDummy.java

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopDummy {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Horse Dummy Test");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(60);

        new Lwjgl3Application(new DummyLauncher(), config);
    }
}

