package horsegame.race;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MapRenderer {
    private final GameMap map;

    public MapRenderer(GameMap map) {
        this.map = map;
    }

    public void render(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);

        // tło
        sr.setColor(Color.BLACK);
        sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float laneHeight = Gdx.graphics.getHeight() / map.getLanes();
        float cellWidth = Gdx.graphics.getWidth() / (float) map.getVisibleCols();

        for (int lane = 0; lane < map.getLanes(); lane++) {
            for (int col = 0; col < map.getVisibleCols(); col++) {
                int obstacle = map.getObstacle(lane, col);
                if (obstacle != 0) {//tutaj jak nie jest zero to rysujemy przeszkodę u nas to na razie czerwony kloc
                    sr.setColor(Color.RED);
                    sr.rect(col * cellWidth, lane * laneHeight, cellWidth, laneHeight);
                }
            }
        }

        sr.end();
    }

    public void dispose() {

    }
}

