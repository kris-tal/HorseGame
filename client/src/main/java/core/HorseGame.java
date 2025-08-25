package core;

import com.badlogic.gdx.Game;
import views.login.LoginScreen;
import views.register.RegisterScreen;
import services.ResourceManager;

public class HorseGame extends Game {
    private ResourceManager resourceManager;
    private LoginScreen loginScreen;
    private RegisterScreen registerScreen;
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
