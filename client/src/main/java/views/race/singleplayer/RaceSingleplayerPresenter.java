package views.race.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import data.HorseType;
import data.race.GameMap;
import race.*;
import race.objects.CoinObject;
import race.objects.EmptyObject;
import race.objects.DeathStoneObject;
import race.objects.BushObject;
import race.objects.ElectricBarrierObject;
import race.objects.PuddleObject;
import animations.HorseAnimationActor;


public class RaceSingleplayerPresenter {
    private RaceWorld world;
    private boolean raceFinished = false;

    public void init() {
        int lanes = 3;
        int length = 300; // 300 kolumn * 10 m = 3000 m (3 km)
        float speed = 4f;

        Texture deathStoneTexture = new Texture("images/race/stone.png");
        Texture coinTexture = new Texture("images/other/coin.png");
        Texture puddleTexture = new Texture("images/race/puddle.png");
        Texture bushTexture = new Texture("images/race/bush.png");
        Texture electricBarrierTexture = new Texture("images/race/electricbarrier.png");

        MapObject[][] mapData = new MapObject[lanes][length];
        for (int lane = 0; lane < lanes; lane++) {
            for (int col = 0; col < length; col++) {
                double r = Math.random();
                if (col <= 2) {
                    mapData[lane][col] = new EmptyObject();
                } else if (r < 0.03) { // DeathStone - najrzadszy
                    mapData[lane][col] = new DeathStoneObject(deathStoneTexture);
                } else if (r < 0.07) { // Coin
                    mapData[lane][col] = new CoinObject(coinTexture);
                } else if (r < 0.1) { // Puddle
                    mapData[lane][col] = new PuddleObject(puddleTexture);
                } else if (r < 0.15) { // Bush
                    mapData[lane][col] = new BushObject(bushTexture);
                } else if (r < 0.17) { // ElectricBarrier (
                    mapData[lane][col] = new ElectricBarrierObject(electricBarrierTexture);
                } else { // reszta pusta
                    mapData[lane][col] = new EmptyObject();
                }
            }
        }

        GameMap map = new GameMap(lanes, length, mapData);
        HorseAnimationActor horseActor = new HorseAnimationActor(HorseType.BORING_HORSE);
        horseActor.setMoving(true);

        Player player = new Player(horseActor, map.getLanes() / 2, speed);
        player.makeSprintAvailable();

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
