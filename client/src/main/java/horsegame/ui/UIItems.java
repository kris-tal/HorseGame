package horsegame.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class UIItems {

    public static TextButton getTextButton(String text) {
        return new TextButton("log in", UIStyles.getDefaultButtonStyle());
    }

    public static Label getLabel(String text) {
        return new Label(text, UIStyles.getDefaultLabelStyle());
    }

    public static Table getTable() {
        Table table = new Table();
        NinePatch background = UIStyles.getDefaultBackgroundPatch();
        table.setBackground(new NinePatchDrawable(background));

        return table;
    }
}
