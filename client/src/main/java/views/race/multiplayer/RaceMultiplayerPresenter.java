package views.race.multiplayer;

import com.badlogic.gdx.Gdx;
import race.config.BaseGameConfig;
import race.factory.GameFactory;
import services.managers.GameManager;
import views.race.singleplayer.RaceSingleplayerPresenter;

public class RaceMultiplayerPresenter extends RaceSingleplayerPresenter {
    private final GameManager gameManager;
    private float progressSendTimer = 0f;
    private final float SENT_INTERVAL = 0.1f; // 100ms

    public RaceMultiplayerPresenter(GameFactory gameFactory, BaseGameConfig raceConfig, GameManager gameManager) {
        super(gameFactory, raceConfig);
        this.gameManager = gameManager;
    }

    public void startRace() {
        gameManager.startGame();
    }

    public void endRace() {
        gameManager.endGame();
    }

    @Override
    public void update(float delta) {
        if (gameStateManager.isRaceFinished())
            return;

        super.update(delta);

        progressSendTimer += delta;
        if (progressSendTimer >= SENT_INTERVAL) {
            progressSendTimer = 0f;
            int curDistance = getDistance();
            gameManager.updatePosition(curDistance);
        }
    }
}
