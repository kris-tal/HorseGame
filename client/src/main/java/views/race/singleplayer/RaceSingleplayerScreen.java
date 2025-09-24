package views.race.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.HorseGame;
import data.horse.HorseData;
import race.RaceWorld;
import services.horse.HorseService;
import services.horse.HorseServiceImpl;
import views.common.base.BaseGameScreen;
import views.race.ui.RaceStatsPanel;
import views.race.ui.SprintWidget;
import views.ranch.RanchScreen;
import java.util.List;

public class RaceSingleplayerScreen extends BaseGameScreen {
    private final RaceSingleplayerPresenter presenter;
    private Label distanceLabel;
    private Label coinLabel;
    private RaceStatsPanel statsPanel;
    private SprintWidget sprintWidget;
    private final HorseService horseService;

    public RaceSingleplayerScreen(HorseGame game) {
        super(game);
        this.horseService = new HorseServiceImpl();

        List<HorseData> horses = horseService.getUserHorses();
        float cooldownReduction = 0f;
        if (horses != null && !horses.isEmpty()) {
            HorseData horse = horses.getFirst();
            cooldownReduction = horse.getTrainingPoints() * 0.0001f;
        }
        
        race.config.BaseGameConfig config = race.config.BaseGameConfig.createRaceConfigWithCooldownReduction(cooldownReduction);
        race.map.MapGenerator mapGenerator = new race.map.RandomMapGenerator();
        race.input.InputHandler inputHandler = new race.input.KeyboardInputHandler();
        
        race.factory.GameFactory raceFactory = new race.factory.GameFactory(mapGenerator, inputHandler, config);
        
        this.presenter = new RaceSingleplayerPresenter(raceFactory, config);
    }

    @Override
    protected void initializeUI() {
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
        statsPanel.updateStats(presenter.getDistance(), presenter.getCoins());
        statsPanel.setVisible(true);
        
        if (!onGameFinished) {
            if (presenter.isPlayerDead()) {
                onGameFinished = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        Gdx.app.postRunnable(() -> game.setScreen(new RanchScreen(game)));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            } else {
                onGameFinished = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        Gdx.app.postRunnable(() -> game.setScreen(new RanchScreen(game)));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            }
        }
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
    }

    @Override
    public void show() {
        super.show();
        presenter.init();
        
        float bw = 500f, bh = 30f;
        if (presenter.getWorld() != null && presenter.getWorld().getPlayer() != null) {
            sprintWidget = new SprintWidget(presenter.getWorld().getPlayer(), bw, bh);
            sprintWidget.setPosition((Gdx.graphics.getWidth() - bw) / 2f, 30f);
            uiStage.addActor(sprintWidget);
        }
    }
}
