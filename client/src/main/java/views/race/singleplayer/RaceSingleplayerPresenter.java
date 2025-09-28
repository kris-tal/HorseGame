package views.race.singleplayer;

import race.config.BaseGameConfig;
import race.factory.GameFactory;
import presentation.common.RacePresenterTemplate;

public class RaceSingleplayerPresenter extends RacePresenterTemplate {

    public RaceSingleplayerPresenter(GameFactory gameFactory, BaseGameConfig raceConfig) {
        super(gameFactory, raceConfig);
    }

    @Override
    public void update(float delta) {
        if (gameStateManager.isRaceFinished()) return;

        world.update(delta);
        gameStateManager.update(world.getPlayer(), world.getMap());
    }

    public int getCoins() {
        return world.getPlayer().getCoins();
    }

    public boolean isRaceCompleted() {
        return gameStateManager.isRaceCompleted();
    }
}
