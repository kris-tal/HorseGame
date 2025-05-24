package game;

import com.badlogic.gdx.Game;
import game.screen.CharacterSelectionScreen;
import game.screen.LoginScreen;
import game.screen.RegisterScreen;
import manager.ResourceManager;

public class HorseGame extends Game {
    private ResourceManager resourceManager;
    private LoginScreen loginScreen;
    private RegisterScreen registerScreen;
    private CharacterSelectionScreen characterSelectionScreen;
    //private RanchScreen ranchScreen;
    //private MeadowScreen meadowScreen;
    //private RaceScreen raceScreen;

    @Override
    public void create() {
        resourceManager = new ResourceManager();
        loginScreen = new LoginScreen(this, resourceManager);
        registerScreen = new RegisterScreen(this, resourceManager);
        setScreen(loginScreen);
    }

    public void showLoginScreen() {
        setScreen(loginScreen);
    }

    public void showRegisterScreen() {
        setScreen(registerScreen);
    }

    @Override
    public void dispose() {
        if(loginScreen != null) loginScreen.dispose();
        if(registerScreen != null) registerScreen.dispose();
    }
}
