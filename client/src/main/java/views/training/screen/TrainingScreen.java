package views.training.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.HorseGame;
import data.horse.HorseData;
import race.RaceWorld;
import services.horse.HorseService;
import services.training.TrainingService;
import views.common.base.BaseGameScreen;
import views.race.ui.RaceStatsPanel;
import views.race.ui.SprintWidget;
import views.ranch.RanchScreen;
import training.contract.TrainingPresenter;

public class TrainingScreen extends BaseGameScreen {
    private final TrainingPresenter presenter;
    private final TrainingService trainingService;
    private final HorseService horseService;
    
    private Label titleLabel;
    private Label distanceLabel;
    private Label trainingPointsLabel;
    private Label horseLevelLabel;
    private Label successfulRacesLabel;
    private Label cooldownReductionLabel;
    private Label longestRunLabel;
    private SprintWidget sprintWidget;
    private RaceStatsPanel statsPanel;

    public TrainingScreen(HorseGame game, TrainingPresenter presenter, TrainingService trainingService, HorseService horseService) {
        super(game);
        this.presenter = presenter;
        this.trainingService = trainingService;
        this.horseService = horseService;
    }

    @Override
    protected void initializeUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        
        titleLabel = new Label("TRAINING MODE", skin);
        distanceLabel = new Label("Distance: 0m", skin);
        trainingPointsLabel = new Label("Training Points: 0/10000", skin);
        horseLevelLabel = new Label("Horse Level: 0", skin);
        successfulRacesLabel = new Label("Successful Races: 0", skin);
        cooldownReductionLabel = new Label("Cooldown Reduction: 0%", skin);
        longestRunLabel = new Label("Longest Run: 0m", skin);
        
        titleLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        distanceLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        trainingPointsLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        horseLevelLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        successfulRacesLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        cooldownReductionLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        longestRunLabel.setStyle(skin.get("default", Label.LabelStyle.class));
        
        table.add(titleLabel).pad(10);
        table.row();
        table.add(distanceLabel).pad(5);
        table.row();
        table.add(trainingPointsLabel).pad(5);
        table.row();
        table.add(horseLevelLabel).pad(5);
        table.row();
        table.add(successfulRacesLabel).pad(5);
        table.row();
        table.add(cooldownReductionLabel).pad(5);
        table.row();
        table.add(longestRunLabel).pad(5);
        
        uiStage.addActor(table);
        
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
        return presenter.isRaceFinished();
    }

    @Override
    protected boolean isPlayerDead() {
        return presenter.isPlayerDead();
    }

    @Override
    protected void handleGameCompletion() {
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
                HorseData horse = horseService.getUserHorses().getFirst();
                presenter.completeTraining(horse.getId());
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
            trainingPointsLabel.setText("Training Points: " + presenter.getTrainingPoints() + "/10000");
            cooldownReductionLabel.setText("Cooldown Reduction: " + 
                String.format("%.2f", presenter.getSprintCooldownReduction() * 100) + "%");
            longestRunLabel.setText("Longest Run: " + presenter.getDisplayLongestRun() + "m");
            
            if (presenter.isPlayerDead()) {
                distanceLabel.setText("Training Failed - Horse Hit Obstacle!");
                trainingPointsLabel.setText("Returning to ranch...");
            }
            
            if (sprintWidget != null && presenter.getWorld() != null && presenter.getWorld().getPlayer() != null) {
                sprintWidget.act(delta);
            }
        } catch (Exception e) {
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
        HorseData horse = horseService.getUserHorses().getFirst();
        if (horse != null) {
            presenter.saveTrainingDataBeforeExit(horse.getId());
        }
        game.setScreen(new RanchScreen(game));
    }
}