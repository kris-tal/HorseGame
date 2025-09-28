// java
package dummylaunchers;

import animations.BaseHorseActor;
import animations.RaceHorseActor;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import data.horse.HorseType;
import data.horse.HorseTypeRegistry;

public class DummyLauncher extends ApplicationAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private BaseHorseActor horse;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(800, 600), batch);

        HorseTypeRegistry.loadDefault();

        HorseType type = HorseTypeRegistry.getByKey("normal_horse");
        if (type == null) {
            type = HorseTypeRegistry.getByKey("boring_horse"); // fallback
        }
        if (type == null) {
            throw new IllegalStateException("Horse types not loaded.");
        }

        horse = new RaceHorseActor(type, 2f);
        horse.setPosition(400 - horse.getWidth() / 2f, 300 - horse.getHeight() / 2f);
        stage.addActor(horse);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            horse.setMoving(!horse.isMoving());
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}