package data.horse;

import com.badlogic.gdx.graphics.Color;

public final class HorsePalette {
    private final Color bodyColor;
    private final Color maneColor;
    private final Color tailColor;

    public HorsePalette(Color bodyColor, Color maneColor, Color tailColor) {
        this.bodyColor = bodyColor;
        this.maneColor = maneColor;
        this.tailColor = tailColor;
    }

    public Color getBodyColor() {
        return bodyColor;
    }

    public Color getManeColor() {
        return maneColor;
    }

    public Color getTailColor() {
        return tailColor;
    }
}
