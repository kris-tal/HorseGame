package views.race.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.HorseGame;
import race.RaceWorld;
import services.managers.ResourceManager;
import views.race.RaceStatsPanel;
import views.ranch.RanchScreen;
import views.race.buttons.SprintButton;

public class RaceSingleplayerScreen implements Screen {
    private final HorseGame game;
    private final RaceSingleplayerPresenter presenter;

    private Stage uiStage, bgStage;
    private SpriteBatch batch;
    private Label distanceLabel;
    private Label coinLabel;
    private RaceStatsPanel statsPanel;
    private boolean statsPanelShown = false;

    public RaceSingleplayerScreen(HorseGame game) {
        this.game = game;
        this.presenter = new RaceSingleplayerPresenter();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bgStage = new Stage(new ScreenViewport());
        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(uiStage);

        Image background = new Image(ResourceManager.raceScreenBgTexture);
        background.setFillParent(true);
        bgStage.addActor(background);

        distanceLabel = new Label("Distance: 0m", ResourceManager.uiSkin);
        distanceLabel.setPosition(30, Gdx.graphics.getHeight() - 50);
        uiStage.addActor(distanceLabel);

        coinLabel = new Label("Coins: 0", ResourceManager.uiSkin);
        coinLabel.setPosition(distanceLabel.getX() + 300, Gdx.graphics.getHeight() - 50);
        uiStage.addActor(coinLabel);

        statsPanel = new RaceStatsPanel(600, 400);
        statsPanel.setX((Gdx.graphics.getWidth() - statsPanel.getWidth()) / 2f);
        statsPanel.setY((Gdx.graphics.getHeight() - statsPanel.getHeight()) / 2f);
        uiStage.addActor(statsPanel);

        statsPanel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!statsPanel.isOpen()) {
                    game.setScreen(new RanchScreen(game));
                }
            }
        });

        presenter.init();

        final views.race.buttons.SprintButton sprintButton = new views.race.buttons.SprintButton(presenter.getWorld().getPlayer(), "SPRINT", ResourceManager.uiSkin);
        float bw = 200f, bh = 60f;
        sprintButton.setSize(bw, bh);
        sprintButton.setPosition((Gdx.graphics.getWidth() - bw) / 2f, 20f);
        uiStage.addActor(sprintButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        presenter.update(delta);

        if (presenter.isRaceFinished() && !statsPanelShown) {
            statsPanel.updateStats(presenter.getDistance(), presenter.getCoins());
            statsPanel.setVisible(true);
            statsPanelShown = true;
            return;
        }

        bgStage.draw();

        batch.begin();
        RaceWorld world = presenter.getWorld();
        world.render(batch);
        batch.end();

        distanceLabel.setText("Distance: " + presenter.getDistance() + "m");
        coinLabel.setText("Coins: " + presenter.getCoins());
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
    }
}
