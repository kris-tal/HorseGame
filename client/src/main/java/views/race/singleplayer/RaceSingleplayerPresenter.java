package views.race.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import data.race.GameMap;
import race.*;
import race.objects.CoinObject;
import race.objects.EmptyObject;
import race.objects.ObstacleObject;
import services.managers.ResourceManager;
import animations.HorseAnimationActor;


public class RaceSingleplayerPresenter {
    private RaceWorld world;
    private boolean raceFinished = false;

    public void init(ResourceManager resourceManager) {
        int lanes = 3;
        int length = 50;
        float speed = 4f;

        Texture obstacleTexture = new Texture("images/race/obstacle.png");
        Texture coinTexture = new Texture("images/other/coin.png");

        MapObject[][] mapData = new MapObject[lanes][length];
        for (int lane = 0; lane < lanes; lane++) {
            for (int col = 0; col < length; col++) {
                double r = Math.random();
                if (r < 0.1 && col > 2) mapData[lane][col] = new ObstacleObject(obstacleTexture);
                else if (r < 0.2 && col > 2) mapData[lane][col] = new CoinObject(coinTexture);
                else mapData[lane][col] = new EmptyObject();
            }
        }

        GameMap map = new GameMap(lanes, length, mapData);
        HorseAnimationActor horseActor = new HorseAnimationActor();
        horseActor.setMoving(true);

        Player player = new Player(horseActor, map.getLanes() / 2, speed);

        world = new RaceWorld(map, player);
    }

    public void update(float delta) {
        if (raceFinished) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        world.update(delta);
        if (! world.getPlayer().isAlive() || world.getPlayer().getCol() > world.getMap().getLength()) {
            raceFinished = true;
        }
    }

    public RaceWorld getWorld() {
        return world;
    }

    public int getDistance() {
        return (int) (10 * world.getPlayer().getCol());
    }

    public int getCoins() {
        return world.getPlayer().getCoins();
    }

    public boolean isRaceFinished() {
        return raceFinished;
    }
}
