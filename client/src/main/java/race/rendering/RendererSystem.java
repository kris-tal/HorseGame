package race.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import data.race.GameMap;
import race.Player;
import race.camera.CameraSystem;
import animations.BaseHorseActor;
import services.managers.ResourceManager;

public class RendererSystem {
    private final CameraSystem camera;

    public RendererSystem(CameraSystem camera) {
        this.camera = camera;
    }

    public void render(SpriteBatch batch, GameMap map, Player player, BaseHorseActor horseActor) {
        renderMap(batch, map);
        renderPlayer(batch, player, horseActor);
        renderVisionEffect(batch, player);
    }

    private void renderMap(SpriteBatch batch, GameMap map) {
        int startCol = (int) camera.getScrollX() - 1;
        int endCol = startCol + camera.getVisibleCols() + 2;

        if (endCol > map.getLength()) {
            endCol = map.getLength();
        }

        for (int lane = 0; lane < map.getLanes(); lane++) {
            for (int col = startCol; col < endCol; col++) {
                if (col >= 0) {
                    var obj = map.getObject(lane, col);
                    if (obj != null) {
                        float x = camera.getScreenX(col);
                        float y = camera.getScreenY(lane);
                        obj.render(batch, x, y, camera.getCellSize());
                    }
                }
            }
        }
    }

    private void renderPlayer(SpriteBatch batch, Player player, BaseHorseActor horseActor) {
        float px = camera.getLeftOffset() + camera.getCellSize() * 0.5f;
        float py = camera.getScreenY(player.getLane());
        
        horseActor.setPosition(px, py);
        horseActor.setSize(camera.getCellSize(), camera.getCellSize());
        horseActor.draw(batch, 1f);
    }

    private void renderVisionEffect(SpriteBatch batch, Player player) {
        if (player.isVisionLimited()) {
            batch.draw(ResourceManager.visionLimitedTexture, 0, 0,
                    Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
}
