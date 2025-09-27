// java
package views.training.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.HorseGame;
import data.horse.HorseData;
import race.RaceWorld;
import services.horse.HorseService;
import services.managers.SessionManagerPort;
import services.training.TrainingService;
import views.common.base.BaseGameScreen;
import views.training.ui.TrainingStatsPanel;
import views.race.ui.SprintWidget;
import views.ranch.RanchScreen;
import training.contract.TrainingPresenter;
import data.race.EquippedHorseService;

public class TrainingScreen extends BaseGameScreen {
    private final TrainingPresenter presenter;
    private final TrainingService trainingService;
    private final HorseService horseService;
    private final SessionManagerPort sessionManager;
    private final EquippedHorseService equippedHorseService;

    private Label titleLabel;
    private Label distanceLabel;
    private Label trainingPointsLabel;
    private Label longestRunLabel;
    private Label coinsLabel;
    private SprintWidget sprintWidget;
    private TrainingStatsPanel statsPanel;

    public TrainingScreen(HorseGame game,
                          TrainingPresenter presenter,
                          TrainingService trainingService,
                          HorseService horseService,
                          SessionManagerPort sessionManager,
                          EquippedHorseService equippedHorseService) {
        super(game, sessionManager, equippedHorseService);
        this.presenter = presenter;
        this.trainingService = trainingService;
        this.horseService = horseService;
        this.sessionManager = sessionManager;
        this.equippedHorseService = equippedHorseService;
    }

    @Override
    protected void initializeUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        titleLabel = new Label("TRAINING MODE", skin);
        distanceLabel = new Label("Distance: 0m", skin);
        trainingPointsLabel = new Label("Training Points: 0/10000", skin);
        longestRunLabel = new Label("Longest Run: 0m", skin);
        coinsLabel = new Label("Coins: 0", skin);

        titleLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        distanceLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        trainingPointsLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        longestRunLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        coinsLabel.setStyle(skin.get("default", Label.LabelStyle.class));

        table.add(titleLabel).pad(10);
        table.row();
        table.add(distanceLabel).pad(5);
        table.row();
        table.add(trainingPointsLabel).pad(5);
        table.row();
        table.add(longestRunLabel).pad(5);
        table.row();
        table.add(coinsLabel).pad(5);

        uiStage.addActor(table);

        statsPanel = new TrainingStatsPanel(600, 400);
        statsPanel.setX((Gdx.graphics.getWidth() - statsPanel.getWidth()) / 2f);
        statsPanel.setY((Gdx.graphics.getHeight() - statsPanel.getHeight()) / 2f);
        statsPanel.setVisible(false);

        uiStage.addActor(statsPanel);
    }

    @Override
    protected RaceWorld getGameWorld() {
        return presenter.getWorld();
    }

    public TrainingPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected boolean isGameFinished() {
        return presenter.isRaceFinished();
    }

    @Override
    protected boolean isPlayerDead() {
        return presenter.isPlayerDead();
    }

    @Override
    protected void handleGameCompletion() {
        statsPanel.setVisible(true);
        try {
            int distance = presenter.getDistance();
            int currentLongest = Integer.parseInt(presenter.getDisplayLongestRun());
            boolean isNewRecord = distance > currentLongest;
            int longestToShow = Math.max(distance, currentLongest);
            int coins = presenter.getCoins();
            statsPanel.updateStats(distance, longestToShow, coins, isNewRecord);
        } catch (Exception ignored) {
        }

        if (!onGameFinished) {
            if (presenter.isPlayerDead()) {
                HorseData horse = resolveHorse();
                if (horse != null) {
                    presenter.saveTrainingDataBeforeExit(horse.getId());
                }
                onGameFinished = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        Gdx.app.postRunnable(() -> game.setScreen(new RanchScreen(game, sessionManager, equippedHorseService)));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            } else {
                HorseData horse = resolveHorse();
                if (horse != null) {
                    presenter.completeTraining(horse.getId());
                }
                onGameFinished = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        Gdx.app.postRunnable(() -> game.setScreen(new RanchScreen(game, sessionManager, equippedHorseService)));
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
            trainingPointsLabel.setText("Training Points: " + presenter.getTrainingPoints() + "/10000");
            longestRunLabel.setText("Longest Run: " + presenter.getDisplayLongestRun() + "m");
            coinsLabel.setText("Coins: " + presenter.getCoins());

            if (sprintWidget != null && presenter.getWorld() != null && presenter.getWorld().getPlayer() != null) {
                sprintWidget.act(delta);
            }
        } catch (Exception e) {
            // intentionally ignored
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

    @Override
    protected void handleEscapeKey() {
        HorseData horse = resolveHorse();
        if (horse != null) {
            presenter.saveTrainingDataBeforeExit(horse.getId());
        }
        game.setScreen(new RanchScreen(game, sessionManager, equippedHorseService));
    }

    private HorseData resolveHorse() {
        try {
            // Prefer equipped horse if available
            HorseData equipped = (equippedHorseService != null) ? equippedHorseService.getEquippedHorse() : null;
            if (equipped != null) {
                return equipped;
            }
        } catch (Throwable ignored) {
            // fall back to user horses list
        }
        try {
            var horses = horseService.getUserHorses();
            if (horses != null && !horses.isEmpty()) {
                return horses.getFirst();
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}