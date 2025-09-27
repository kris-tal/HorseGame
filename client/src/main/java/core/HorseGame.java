// java
package core;

import com.badlogic.gdx.Game;
import data.race.EquippedHorseService;
import views.login.LoginScreen;
import views.training.screen.TrainingScreen;
import views.training.manager.TrainingManager;
import race.map.RandomMapGenerator;
import race.input.KeyboardInputHandler;
import services.managers.ResourceManager;
import services.managers.SessionManagerPort;

public class HorseGame extends Game {
    private ResourceManager resourceManager;
    private LoginScreen loginScreen;

    @Override
    public void create() {
        resourceManager = new ResourceManager();
        loginScreen = new LoginScreen(this);
        setScreen(loginScreen);
    }

    public void showLoginScreen() {
        setScreen(loginScreen);
    }

    public void showTrainingScreen(SessionManagerPort sessionManager, EquippedHorseService equippedHorseService) {
        RandomMapGenerator mapGenerator = new RandomMapGenerator();
        KeyboardInputHandler inputHandler = new KeyboardInputHandler();
        TrainingManager trainingManager = new TrainingManager(mapGenerator, inputHandler, sessionManager, equippedHorseService);
        TrainingScreen trainingScreen = trainingManager.createTrainingScreen(this);
        setScreen(trainingScreen);
    }

    @Override
    public void dispose() {
        if (loginScreen != null) loginScreen.dispose();
    }
}
