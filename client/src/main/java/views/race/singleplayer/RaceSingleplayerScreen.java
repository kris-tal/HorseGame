package views.race.singleplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import core.HorseGame;
import data.race.GameMap;
import race.MapRenderer;
import services.ResourceManager;

public class RaceSingleplayerScreen implements Screen {
    private final HorseGame game;
    private final ResourceManager resourceManager;

    private ShapeRenderer shapeRenderer;
    private GameMap gameMap;
    private MapRenderer renderer;

    private final int lanes = 3;
    private final int mapLength = 50;
    private final int visibleCols = 10;

    public RaceSingleplayerScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();

        int[][][] mapData = new int[lanes][mapLength][1];
        for (int lane = 0; lane < lanes; lane++) {
            for (int col = 0; col < mapLength; col++) {
                mapData[lane][col][0] = Math.random() < 0.2 ? 1 : 0;
            }
        }

        gameMap = new GameMap(lanes, mapLength, mapData, visibleCols);
        renderer = new MapRenderer(gameMap);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameMap.scroll();
        }

        renderer.render(shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (renderer != null) renderer.dispose();
    }
}
