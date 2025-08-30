package animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HorseAnimationActor extends Actor {
    private final Animation<TextureRegion> walkAnimation;
    private final TextureRegion idleFrame;
    private float stateTime = 0f;
    private boolean moving = false;
    private boolean facingRight = true;

    private final float scale = 3f;

    public HorseAnimationActor() {
        Texture texture = new Texture(Gdx.files.internal("horses/animations/run.png"));

        TextureRegion frame1 = new TextureRegion(texture, 44, 2, 40, 32);
        TextureRegion frame2 = new TextureRegion(texture, 128, 2, 40, 32);
        TextureRegion frame3 = new TextureRegion(texture, 86, 2, 40, 32); // idle
        TextureRegion frame4 = new TextureRegion(texture, 2, 2, 40, 32);

        walkAnimation = new Animation<>(0.1f, frame1, frame2, frame3, frame4);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        idleFrame = frame3;

        // 3 razy większy rozmiar
        setSize(frame1.getRegionWidth() * scale, frame1.getRegionHeight() * scale);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (moving) stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;
        if (moving) {
            currentFrame = walkAnimation.getKeyFrame(stateTime);
        } else {
            currentFrame = idleFrame;
        }

        float width = getWidth();
        float height = getHeight();
        float drawX = getX();
        float drawY = getY();

        if (!facingRight) {
            batch.draw(currentFrame, drawX + width, drawY, -width, height);
        } else {
            batch.draw(currentFrame, drawX, drawY, width, height);
        }
    }

    // tutaj ustawia to czy idzie w lewo czy w prawo
    public void moveTo(float x, float y) {
        if (x > getX()) facingRight = true;
        else if (x < getX()) facingRight = false;

        setPosition(x, y);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
        if (!moving) stateTime = 0f;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isFacingRight() {
        return facingRight;
    }
}
