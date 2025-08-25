package horsegame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import horsegame.HorseGame;
import horsegame.ui.*;
import horsegame.ui.context.MenuContext;
import manager.ResourceManager;

public class RanchScreen implements Screen {
    private final HorseGame game;
    private final ResourceManager resourceManager;
    private Stage stage;
    private final MenuContext menuContext;

    private RaceMenuPanel raceMenuPanel;
    private HorseSelectionPanel horseSelectionPanel;
    private LobbyPanel lobbyPanel;

    public RanchScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
        menuContext = new MenuContext();
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

        RanchStatusBar statusBar = getStatusBar(skin);
        stage.addActor(statusBar);

        raceMenuPanel = new RaceMenuPanel(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        raceMenuPanel.setListener(mode -> {
            switch (mode) {
                case SINGLEPLAYER:
                    menuContext.setMode(MenuContext.Mode.SINGLEPLAYER);
                    showPanel(horseSelectionPanel);
                    break;
                case MULTIPLAYER:
                    menuContext.setMode(MenuContext.Mode.MULTIPLAYER);
                    showPanel(horseSelectionPanel);
                    break;
            }
            raceMenuPanel.hide();
        });

        horseSelectionPanel = new HorseSelectionPanel(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        lobbyPanel = new LobbyPanel(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        //menu panels
        stage.addActor(raceMenuPanel);
        stage.addActor(horseSelectionPanel);
        stage.addActor(lobbyPanel);

        //shop panels
        //nie ma jeszcze

        hideAllPanels();
    }

    private RanchStatusBar getStatusBar(Skin skin) {
        RanchStatusBar statusBar = new RanchStatusBar(game, resourceManager, skin);
        statusBar.setListener(action -> {
            switch (action) {
                case SHOP:
                    Gdx.app.log("DEBUG", "Shop button pressed");
                    game.setScreen(new ShopScreen(skin));
                    break;
                case RACE:
                    openMenuPanel();
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

    public void openMenuPanel() {
        raceMenuPanel.show(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void  hideAllPanels() {
        raceMenuPanel.hide();
        horseSelectionPanel.hide();
        lobbyPanel.hide();
    }

    private void showPanel(Panel panel) {
        hideAllPanels();
        panel.show(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void onRaceMenuButtonClicked() {
        showPanel(raceMenuPanel);
    }

    private void onSingleplayerChosen() {
        showPanel(horseSelectionPanel);
    }

    private void onMultiplayerChosen() {
        showPanel(horseSelectionPanel);
        // after horse selection:
        // showPanel(lobbyPanel);
    }
}
