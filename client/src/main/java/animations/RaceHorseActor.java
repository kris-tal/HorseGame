package animations;

import data.horse.HorseData;
import data.horse.HorseType;

public class RaceHorseActor extends BaseHorseActor {
    private float speed = 200f;

    public RaceHorseActor(HorseData horseData) {
        super(horseData);
        setMoving(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float newX = getX() + speed * delta;
        moveTo(newX, getY());
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
