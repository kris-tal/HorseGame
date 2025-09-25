package dummylaunchers;

// DummyLauncher.java

import animations.RaceHorseActor;
import animations.StrollingHorseActor;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import animations.BaseHorseActor;
import animations.IdleHorseActor;
import data.horse.HorseData;
import data.horse.HorseType;

public class DummyLauncher extends ApplicationAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private BaseHorseActor horse;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(800, 600), batch);

        HorseData horseData = new HorseData();

        // pick any HorseType you defined
        horse = new RaceHorseActor(HorseType.NORMAL_HORSE, 2f);
        horse.setPosition(400 - horse.getWidth() / 2, 300 - horse.getHeight() / 2);

        stage.addActor(horse);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // toggle movement with space bar
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

