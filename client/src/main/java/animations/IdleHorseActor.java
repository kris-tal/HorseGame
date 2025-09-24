package animations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import data.horse.HorseData;
import data.horse.HorseType;
import services.managers.ResourceManager;

public class IdleHorseActor extends BaseHorseActor {
    private final TextureRegion region;

    public IdleHorseActor(HorseType horseType, float scale) {
        super(horseType, scale);
        this.region = ResourceManager.horsesAtlas.findRegion(horseType.getAtlasKey());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // optional idle animation logic here
    }
}

