// java
package core;

import com.badlogic.gdx.Game;
import services.media.AudioService;
import views.login.LoginScreen;
import services.managers.SessionManagerPort;

public class HorseGame extends Game {
    private SessionManagerPort sessionManager;
    private AudioService audio;
    private LoginScreen loginScreen;

    public HorseGame(SessionManagerPort sessionManager, AudioService audio) {
        this.sessionManager = sessionManager;
        this.audio = audio;
        loginScreen = new LoginScreen(this, audio);
    }

    @Override
    public void create() {
        setScreen(loginScreen);
    }

    @Override
    public void dispose() {
        if (loginScreen != null) loginScreen.dispose();
    }
}
