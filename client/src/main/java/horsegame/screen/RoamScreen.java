package horsegame.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import horsegame.HorseGame;
import horsegame.ui.StatusBar;
import manager.ResourceManager;

public abstract class RoamScreen implements Screen {
    final HorseGame game;
    private final ResourceManager resourceManager;
    private Stage stage;
    private Skin skin;

    public RoamScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
        this.skin = ResourceManager.uiSkin;
    }

    @Override
    public void show() {
        stage = new Stage();
    }

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }

    public void showStatusBar(ScreenID screenID) {
        StatusBar statusBar = new StatusBar(this.skin, StatusBar.Type.LIGHT, screenID);
        stage.addActor(statusBar);
    }

}
