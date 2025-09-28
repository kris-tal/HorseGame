package race.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

public class RendererSystem {

    private final List<Renderer> renderers;

    public RendererSystem(List<Renderer> renderers) {
        this.renderers = renderers;
    }

    public void render(SpriteBatch batch) {
        for (Renderer renderer : renderers) {
            renderer.render(batch);
        }
    }
}
