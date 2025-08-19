package horsegame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import horsegame.HorseGame;
import horsegame.ui.RaceMenuPanel;
import horsegame.ui.RanchStatusBar;
import manager.ResourceManager;

public class RanchScreen implements Screen {
    private final HorseGame game;
    private final ResourceManager resourceManager;
    private Stage stage;

    private Table dimBackground;
    private RaceMenuPanel raceMenuPanel;

    public RanchScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = ResourceManager.uiSkin;
        Texture bgTexture = ResourceManager.ranchScreenBgTexture;


        //staging bg
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        dimBackground = new Table();
        dimBackground.setFillParent(true);
        dimBackground.setColor(0, 0, 0, 0.5f);
        dimBackground.setVisible(false);

        stage.addActor(dimBackground);

        raceMenuPanel = new RaceMenuPanel(
                Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f
        );
        raceMenuPanel.setPosition(
                (Gdx.graphics.getWidth() - raceMenuPanel.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - raceMenuPanel.getHeight()) / 2f
        );

        stage.addActor(raceMenuPanel);

        RanchStatusBar statusBar = getStatusBar(skin);
        stage.addActor(statusBar);

    }

    private RanchStatusBar getStatusBar(Skin skin) {
        RanchStatusBar statusBar = new RanchStatusBar(game, resourceManager, skin);
        statusBar.setListener(action -> {
            switch (action) {
                case SHOP:
                    //add shop
                    Gdx.app.log("DEBUG", "Shop button pressed");
                    break;
                case RACE:
                    toggleMenu();
                    break;
                case RANKING:
                    Gdx.app.log("DEBUG", "Ranking button pressed");
                    break;
            }
        });
        return statusBar;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        stage.act(delta);
        stage.draw();
    }

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

    public void toggleMenu() {
        if (raceMenuPanel.isOpen()) {
            raceMenuPanel.hide();
            dimBackground.setVisible(false);
        } else {
            raceMenuPanel.show(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            dimBackground.setVisible(true);
        }
    }
}
