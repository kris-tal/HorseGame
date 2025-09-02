package views.ranch;

import animations.HorseAnimationActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.HorseGame;
import data.HorseType;
import views.common.Panel;
import views.race.singleplayer.RaceSingleplayerScreen;
import views.ranch.race.HorseSelectionPanel;
import views.ranch.race.LobbyPanel;
import views.ranch.race.RaceMenuPanel;
import views.ranch.shop.ShopPanel;
import views.common.MenuContext;
import services.managers.ResourceManager;

public class RanchScreen implements Screen, RanchView {
    private final HorseGame game;
    private final RanchPresenter presenter;
    private final MenuContext menuContext;
    private Stage stage;

    private RaceMenuPanel raceMenuPanel;
    private HorseSelectionPanel horseSelectionPanel;
    private LobbyPanel lobbyPanel;
    private ShopPanel shopPanel;

    private Array<HorseAnimationActor> horses = new Array<>();
    private final Array<Float> targetX = new Array<>();
    private final Array<Float> targetY = new Array<>();
    private final Array<Float> waitTimers = new Array<>();
    private Rectangle fence;

    public RanchScreen(HorseGame game) {
        this.game = game;
        this.menuContext = new MenuContext();
        this.presenter = new RanchPresenter(this, menuContext);
    }

    @Override
    public void show() {
        fence = new Rectangle(100, 100, 1550, 600);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = ResourceManager.uiSkin;
        for (int i = 0; i < 20; i++) {
            HorseAnimationActor horse = new HorseAnimationActor(HorseType.BORING_HORSE);
            float x = MathUtils.random(fence.x, fence.x + fence.width);
            float y = MathUtils.random(fence.y, fence.y + fence.height);
            horse.setPosition(x, y);

            horses.add(horse);
            stage.addActor(horse);
            float targetXval = MathUtils.random(fence.x, fence.x + fence.width);
            float targetYval = MathUtils.random(fence.y, fence.y + fence.height);
            targetX.add(targetXval);
            targetY.add(targetYval);

            waitTimers.add(0f);
        }
        Texture bgTexture = ResourceManager.ranchScreenBgTexture;
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
        background.toBack();

        RanchStatusBar statusBar = getStatusBar(skin);
        stage.addActor(statusBar);

        float panelWidth = Gdx.graphics.getWidth() / 2f;
        float panelHeight = Gdx.graphics.getHeight() / 2f;

        raceMenuPanel = new RaceMenuPanel(panelWidth, panelHeight);
        horseSelectionPanel = new HorseSelectionPanel(panelWidth, panelHeight);
        lobbyPanel = new LobbyPanel(panelWidth, panelHeight);
        shopPanel = new ShopPanel(panelWidth, panelHeight);
        shopPanel.setOnPurchaseComplete(result -> {
            statusBar.updateMoney(result.newBalance);
        });

        stage.addActor(raceMenuPanel);
        stage.addActor(horseSelectionPanel);
        stage.addActor(lobbyPanel);
        stage.addActor(shopPanel);

        hideAllPanels();
    }

    private RanchStatusBar getStatusBar(Skin skin) {
        RanchStatusBar statusBar = new RanchStatusBar(game, skin);
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
        shopPanel.hide();
        horseSelectionPanel.hide();
        lobbyPanel.hide();
    }


    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        stage.act(delta);
        stage.draw();
        /// For debug purposes
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new RaceSingleplayerScreen(game));
        }
        stage.act(delta);
        stage.draw();

        for (int i = 0; i < horses.size; i++) {
            HorseAnimationActor horse = horses.get(i);

            if (waitTimers.get(i) > 0) {
                // kon stoi i czeka
                waitTimers.set(i, waitTimers.get(i) - delta);
                horse.setMoving(false); // idle
                continue;
            }

            float dx = targetX.get(i) - horse.getX();
            float dy = targetY.get(i) - horse.getY();
            float distance = (float) Math.sqrt(dx*dx + dy*dy);

            if (distance < 2f) {
                waitTimers.set(i, 10f);
                float newTargetX = MathUtils.random(fence.x, fence.x + fence.width);
                float newTargetY = MathUtils.random(fence.y, fence.y + fence.height);
                targetX.set(i, newTargetX);
                targetY.set(i, newTargetY);

                horse.setMoving(false);
            } else {
                float horseSpeed = 100f;
                float moveX = dx / distance * horseSpeed * delta;
                float moveY = dy / distance * horseSpeed * delta;
                horse.moveTo(horse.getX() + moveX, horse.getY() + moveY);

                horse.setMoving(true);
            }
        }

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
