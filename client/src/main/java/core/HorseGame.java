package core;

import com.badlogic.gdx.Game;
import views.login.LoginScreen;
import views.register.RegisterScreen;
import views.training.screen.TrainingScreen;
import views.training.manager.TrainingManager;
import race.map.TrainingMapGenerator;
import race.input.KeyboardInputHandler;
import services.managers.ResourceManager;

public class HorseGame extends Game {
    private ResourceManager resourceManager;
    private LoginScreen loginScreen;
    private RegisterScreen registerScreen;
    //private RaceScreen raceScreen;

    @Override
    public void create() {
        resourceManager = new ResourceManager();
        loginScreen = new LoginScreen(this);
        registerScreen = new RegisterScreen(this);
        setScreen(loginScreen);
    }

    public void showLoginScreen() {
        setScreen(loginScreen);
    }

    public void showRegisterScreen() {
        setScreen(registerScreen);
    }

    public void showTrainingScreen() {
        TrainingMapGenerator mapGenerator = new TrainingMapGenerator();
        KeyboardInputHandler inputHandler = new KeyboardInputHandler();
        TrainingManager trainingManager = new TrainingManager(mapGenerator, inputHandler);
        TrainingScreen trainingScreen = trainingManager.createTrainingScreen(this);
        setScreen(trainingScreen);
    }

    @Override
    public void dispose() {
        if(loginScreen != null) loginScreen.dispose();
        if(registerScreen != null) registerScreen.dispose();
    }
}
