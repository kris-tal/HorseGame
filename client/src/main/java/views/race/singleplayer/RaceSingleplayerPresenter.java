package views.race.singleplayer;

import race.*;
import race.config.BaseGameConfig;
import race.factory.GameFactory;
import race.state.GameStateManager;
import presentation.common.BaseGamePresenter;

public class RaceSingleplayerPresenter extends BaseGamePresenter {

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
