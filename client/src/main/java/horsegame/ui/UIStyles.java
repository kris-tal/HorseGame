package horsegame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import manager.ResourceManager;

public class UIStyles {
    private final static TextureAtlas atlas = ResourceManager.uiAtlas;
    private final static Color defaultfontColor = new Color(216/255f, 204/255f, 193/255f, 1f);

    public static TextButton.TextButtonStyle getDefaultButtonStyle() {
        NinePatch upPatch = atlas.createPatch("color6_up");
        NinePatch downPatch = atlas.createPatch("color6_down");

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchDrawable(upPatch);
        style.down = new NinePatchDrawable(downPatch);
        style.font = ResourceManager.uiFont;
        style.fontColor = defaultfontColor;
        style.font.getData().setScale(0.5f);

        return style;
    }

    public static TextField.TextFieldStyle getTextFieldStyle() {
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();

        NinePatch background = atlas.createPatch("color5_down");
        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(background);
        backgroundDrawable.setLeftWidth(backgroundDrawable.getLeftWidth() + 10);
        style.background = backgroundDrawable;

        style.font = ResourceManager.uiFont;
        style.fontColor = defaultfontColor;

        return style;
    }

    public static Label.LabelStyle getDefaultLabelStyle() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = defaultfontColor;
        style.font = ResourceManager.uiFont;

        return style;
    }
}
