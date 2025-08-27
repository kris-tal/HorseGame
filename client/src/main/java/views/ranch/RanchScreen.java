package views.ranch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.HorseGame;
import views.common.Panel;
import views.ranch.race.HorseSelectionPanel;
import views.ranch.race.LobbyPanel;
import views.ranch.race.RaceMenuPanel;
import views.ranch.shop.ShopPanel;
import views.common.MenuContext;
import services.managers.ResourceManager;

public class RanchScreen implements Screen, RanchView {
    private final HorseGame game;
    private final ResourceManager resourceManager;
    private final RanchPresenter presenter;
    private final MenuContext menuContext;
    private Stage stage;

    private RaceMenuPanel raceMenuPanel;
    private HorseSelectionPanel horseSelectionPanel;
    private LobbyPanel lobbyPanel;
    private ShopPanel shopPanel;

    public RanchScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
        this.menuContext = new MenuContext();
        this.presenter = new RanchPresenter(this, menuContext);
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
        horseSelectionPanel = new HorseSelectionPanel(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        lobbyPanel = new LobbyPanel(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        raceMenuPanel.setListener(mode -> {
            switch (mode) {
                case SINGLEPLAYER:
                    presenter.onSingleplayerClicked(horseSelectionPanel);
                    break;
                case MULTIPLAYER:
                    presenter.onMultiplayerClicked(horseSelectionPanel);
                    break;
            }
            raceMenuPanel.hide();
        });

        float panelWidth = Gdx.graphics.getWidth() / 2f;
        float panelHeight = Gdx.graphics.getHeight() / 2f;

        horseSelectionPanel = new HorseSelectionPanel(panelWidth, panelHeight);
        lobbyPanel = new LobbyPanel(panelWidth, panelHeight);
        shopPanel = new ShopPanel(panelWidth, panelHeight);

        //menu panels
        stage.addActor(raceMenuPanel);
        stage.addActor(horseSelectionPanel);
        stage.addActor(lobbyPanel);

        //shop panels
        stage.addActor(shopPanel);

        hideAllPanels();
    }

    private RanchStatusBar getStatusBar(Skin skin) {
        RanchStatusBar statusBar = new RanchStatusBar(game, resourceManager, skin);
        statusBar.setListener(action -> {
            switch (action) {
                case SHOP:
                    presenter.onShopButtonClicked(shopPanel);
                    break;
                case RACE:
                    presenter.onRaceButtonClicked(raceMenuPanel);
                    break;
                case RANKING:
                    //presenter.onRankingButtonClicked(null);
                    break;
            }
        });
        return statusBar;
    }

    //RanchView methods

    public void showPanel(Panel panel) {
        hideAllPanels();
        panel.show(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void hideAllPanels() {
        raceMenuPanel.hide();
        horseSelectionPanel.hide();
        lobbyPanel.hide();
    }

    //LibGDX things

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

    public void dispose() {}
}
