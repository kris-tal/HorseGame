package race;

import animations.HorseAnimationActor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import data.horse.HorseType;

public class HorseVisualComponent {
    private final HorseAnimationActor horseActor;
    private final HorseType horseType;

    public HorseVisualComponent(HorseType horseType) {
        this.horseType = horseType;
        this.horseActor = new HorseAnimationActor(horseType);
        this.horseActor.setMoving(true);
    }

    public void update(float delta) {
        horseActor.act(delta);
    }

    public void render(SpriteBatch batch, float x, float y, float size) {
        horseActor.setPosition(x, y);
        horseActor.setSize(size, size);
        horseActor.draw(batch, 1f);
    }

    public void setMoving(boolean moving) {
        horseActor.setMoving(moving);
    }

    public HorseType getHorseType() {
        return horseType;
    }

    public HorseAnimationActor getAnimationActor() {
        return horseActor;
    }
}
