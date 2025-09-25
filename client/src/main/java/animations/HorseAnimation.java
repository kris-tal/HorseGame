package animations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface HorseAnimation {
    TextureRegion getBodyFrame(float stateTime, boolean moving);

    TextureRegion getManeFrame(float stateTime, boolean moving);

    TextureRegion getTailFrame(float stateTime, boolean moving);
}
