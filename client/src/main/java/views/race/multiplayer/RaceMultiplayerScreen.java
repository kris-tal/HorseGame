package views.race.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import core.HorseGame;
import data.lobby.PlayerInfoClient;
import data.race.EquippedHorseService;
import race.RaceWorld;
import services.managers.GameManager;
import services.managers.SessionManagerPort;
import services.profile.ProfileService;
import services.profile.ProfileServiceImpl;
import views.common.base.BaseGameScreen;
import views.race.ui.RaceStatsPanel;
import views.race.ui.SprintWidget;
import views.race.ui.ProgressBarWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Multiplayer race game loop screen
public class RaceMultiplayerScreen extends BaseGameScreen {
    private final RaceMultiplayerPresenter presenter;
    private Label distanceLabel;
    private Label coinLabel;
    private RaceStatsPanel statsPanel;
    private SprintWidget sprintWidget;
    private final GameManager gameManager;
    private Table scoreboardTable;
    // Constants for the race progress bar
    private final float BAR_WIDTH = 300f;
    private final float BAR_HEIGHT = 18f;
    private final int FINISH_DISTANCE;
    // misc
    private final String localUsername;

    public RaceMultiplayerScreen(HorseGame game, SessionManagerPort sessionManager, EquippedHorseService equippedHorseService, GameManager gameManager) {
        super(game, sessionManager, equippedHorseService);
        this.gameManager = gameManager;

        race.config.BaseGameConfig config = race.config.GameConfigFactory.createRaceConfigWithEquippedHorse(equippedHorseService);
        race.map.MapGenerator mapGenerator = new race.map.RandomMapGenerator();
        race.input.InputHandler inputHandler = new race.input.KeyboardInputHandler();

        race.factory.GameFactory raceFactory = new race.factory.GameFactory(mapGenerator, inputHandler, config);

        presenter = new RaceMultiplayerPresenter(raceFactory, config, gameManager);
        presenter.init();
        FINISH_DISTANCE = presenter.getWorld().getMap().getLength();

        ProfileService profileService = new ProfileServiceImpl(sessionManager);
        localUsername = profileService.getUserData().getUsername();

        // DEBUG: Start the race
        gameManager.startGame();
    }

    @Override
    public void initializeUI() {

        distanceLabel = new Label("Distance: 0m", skin);
        coinLabel = new Label("Coins: 0", skin);

        distanceLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        coinLabel.setStyle(skin.get("default", Label.LabelStyle.class));

        uiStage.addActor(distanceLabel);
        uiStage.addActor(coinLabel);

        statsPanel = new RaceStatsPanel(600, 400);
        statsPanel.setX((Gdx.graphics.getWidth() - statsPanel.getWidth()) / 2f);
        statsPanel.setY((Gdx.graphics.getHeight() - statsPanel.getHeight()) / 2f);
        statsPanel.setVisible(false);

        uiStage.addActor(statsPanel);

        scoreboardTable = new Table();
        scoreboardTable.setWidth(BAR_WIDTH + 200);
        scoreboardTable.setPosition(Gdx.graphics.getWidth() - scoreboardTable.getWidth() - 20f, Gdx.graphics.getHeight() - 120f);
        scoreboardTable.align(Align.topRight);
        uiStage.addActor(scoreboardTable);
    }

    @Override
    protected RaceWorld getGameWorld() {
        return presenter.getWorld();
    }

    @Override
    protected boolean isGameFinished() {
        return presenter.isGameFinished();
    }

    @Override
    protected boolean isPlayerDead() {
        return presenter.isPlayerDead();
    }

    @Override
    protected void handleGameCompletion() {
        // TODO !!!
    }

    @Override
    protected void updateGame(float delta) {
        presenter.update(delta);
    }

    @Override
    protected void updateUI(float delta) {
        try {
            distanceLabel.setText("Distance: " + presenter.getDistance() + "m");
            coinLabel.setText("Coins: " + presenter.getCoins());

            if (presenter.isPlayerDead()) {
                distanceLabel.setText("Race Failed - Horse Hit Obstacle!");
                coinLabel.setText("Returning to ranch...");
            }

            if (sprintWidget != null && presenter.getWorld() != null && presenter.getWorld().getPlayer() != null) {
                sprintWidget.act(delta);
            }
        } catch (Exception e) {
            // Handle errors silently
        }

        updateScoreboard(delta);
    }

    private void updateScoreboard(float delta) {
        // snapshot to avoid concurrent writes
        Map<String, PlayerInfoClient> snapshot = gameManager.getCurrentPlayers();
        if (snapshot == null || snapshot.isEmpty()) {
            scoreboardTable.clear();
            return;
        }

        List<PlayerInfoClient> players = new ArrayList<>(snapshot.values());
        players.sort((a, b) -> Integer.compare(b.getProgress(), a.getProgress()));

        // rebuild rows each frame
        scoreboardTable.clear();
        for (int i = 0; i < players.size(); i++) {
            PlayerInfoClient p = players.get(i);
            String name = p.getUsername();
            int distance = p.getProgress();
            Gdx.app.log("scoreboard", "player " + name + " distance:" + distance);

            // name and distance label
            Label nameLabel = new Label((i + 1) + ". " + name, skin);
            nameLabel.setColor(p.getUsername().equals(localUsername) ? Color.GOLD : Color.WHITE);
            Label distLabel = new Label(distance + "m", skin);

            // progress bar widget
            float progressPercent = Math.min(1f, (float) distance / (float) FINISH_DISTANCE);
            ProgressBarWidget bar = new ProgressBarWidget(BAR_WIDTH, BAR_HEIGHT, skin, progressPercent);

            Table row = new Table();
            row.add(nameLabel).width(120).left().padRight(8f);
            row.add(bar).width(BAR_WIDTH).height(BAR_HEIGHT).padRight(8f);
            row.add(distLabel).width(60).right();

            scoreboardTable.add(row).padBottom(6f);
            scoreboardTable.row();
        }
    }

    private int getFinishDistance() {
        try {
            return presenter.getWorld().getMap().getLength();
        } catch (Exception ignored) {
            return FINISH_DISTANCE;
        }
    }

    @Override
    public void show() {
        super.show();

        float bw = 500f, bh = 30f;
        if (presenter.getWorld() != null && presenter.getWorld().getPlayer() != null) {
            sprintWidget = new SprintWidget(presenter.getWorld().getPlayer(), bw, bh);
            sprintWidget.setPosition((Gdx.graphics.getWidth() - bw) / 2f, 30f);
            uiStage.addActor(sprintWidget);
        }
    }
}
