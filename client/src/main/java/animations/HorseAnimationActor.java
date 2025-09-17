package animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import data.horse.HorseType;
import services.managers.ResourceManager;

public class HorseAnimationActor extends Actor {
    private final HorseType horseType;
    private final Animation<TextureRegion> danceAnimation;
    private final TextureRegion idleFrame;
    private float stateTime = 0f;
    private boolean moving = false;
    private boolean facingRight = true;

    private final float scale = 3f;

    public HorseAnimationActor(HorseType horseType) {
        this.horseType = horseType;

        TextureAtlas animationsAtlas = ResourceManager.horseAnimationsAtlas;
        Array<TextureAtlas.AtlasRegion> danceFrames = findAnimationRegions(animationsAtlas, horseType.getAtlasKey(AnimationAction.DANCE.getName()));

        danceAnimation = new Animation<>(AnimationAction.DANCE.getFrameDuration(), danceFrames, AnimationAction.DANCE.getPlayMode());


        TextureAtlas idleFrameAtlas = ResourceManager.horsesAtlas;
        idleFrame = idleFrameAtlas.findRegion(horseType.getAtlasKey());



        // 3 razy większy rozmiar
        setSize(idleFrame.getRegionWidth() * scale, idleFrame.getRegionHeight() * scale);
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
            currentFrame = danceAnimation.getKeyFrame(stateTime);
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

    public static Array<TextureAtlas.AtlasRegion> findAnimationRegions(TextureAtlas atlas, String prefix) {
        Array<TextureAtlas.AtlasRegion> frames = new Array<>();
        int index = 1;
        while (true) {
            TextureAtlas.AtlasRegion frame = atlas.findRegion(prefix + "_" + index);
            if (frame == null) break;
            frames.add(frame);
            index++;
        }
        return frames;
    }
}
