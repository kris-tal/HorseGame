package race.VisualComponent;

import animations.BaseHorseActor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import data.horse.HorseType;

//czy tu w ogole chcemy miec horseData

public class HorseVisualComponent implements VisualComponent {
    private final BaseHorseActor horseActor;

    public HorseVisualComponent(BaseHorseActor horseActor) {
        this.horseActor = horseActor;
        this.horseActor.setMoving(true);
    }

    @Override
    public void update(float delta) {
        horseActor.act(delta);
    }

    @Override
    public void render(SpriteBatch batch, float x, float y, float size) {
        horseActor.setPosition(x, y);
        horseActor.setSize(size, size);
        horseActor.draw(batch, 1f);
    }

    public void setMoving(boolean moving) {
        horseActor.setMoving(moving);
    }


    public BaseHorseActor getAnimationActor() {
        return horseActor;
    }
}
