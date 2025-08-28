package race;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import data.race.GameMap;

public class RaceWorld {
    private final GameMap map;
    private final Player player;
    private final CollisionHandler collisionHandler;

    private float scrollX = 0f;
    private int visibleCols = 10;
    private final float cellSize;

    private final float leftOffset;
    private final float downOffset;

    public RaceWorld(GameMap map, Player player) {
        this.map = map;
        this.player = player;
        this.collisionHandler = new CollisionHandler();

        // Make cells as big as possible
        int maxCellHeight = Gdx.graphics.getWidth() / (visibleCols);
        int maxCellWidth = Gdx.graphics.getHeight() / (map.getLanes());

        cellSize = Math.max(32, Math.min(maxCellWidth, maxCellHeight));
        leftOffset = (Gdx.graphics.getWidth() - visibleCols * cellSize) / 2;
        downOffset = (Gdx.graphics.getHeight() - map.getLanes() * cellSize) / 2;
    }

    public void update(float delta) {
        if (!player.isAlive()) {
            return;
        }

        scrollX = player.getCol();
        if (scrollX > map.getLength() - visibleCols) {
            scrollX = map.getLength() - visibleCols;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.moveUp();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.moveDown();
        }
        player.update(delta);
        collisionHandler.checkCollisions(player, map);

        if (!player.isAlive()) {
            Gdx.app.log("RaceWorld", "Player is dead");
        }
    }

    public void render(SpriteBatch batch) {
        scrollX = player.getCol();
        int startCol = (int) scrollX - 1;
        int endCol = Math.min(startCol + visibleCols + 1, map.getLength()) + 1;

        for (int lane = 0; lane < map.getLanes(); lane++) {
            for (int col = startCol; col < endCol; col++) {
                MapObject obj = map.getObject(lane, col);
                if (obj != null) {
                    float x = (col - scrollX) * cellSize;
                    float y = lane * cellSize;
                    obj.render(batch, x+leftOffset, y+downOffset, cellSize);
                }
            }
        }

        // player at fixed X (left edge)
        float px = 0;
        float py = player.getLane() * cellSize;
        player.render(batch, px+leftOffset, py+downOffset, cellSize);
    }

    public float getScrollX() { return scrollX; };
    public Player getPlayer() { return player; }
    public GameMap getMap() { return map; }
}