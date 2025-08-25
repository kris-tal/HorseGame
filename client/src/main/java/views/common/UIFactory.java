package views.common;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

public class UIFactory {
    private static final float SCALE = 1.5f;
    //buttons

    public static TextButton createTextButton(String text) {
        return new TextButton(text, UIStyles.getDefaultButtonStyle());
    }

    public static TextButton createLargeTextButton(String text) {
        TextButton button = new TextButton(text, UIStyles.getDefaultButtonStyle());
        button.setTransform(true);
        button.setOrigin(Align.center);
        button.setScale(1.5f);
        return button;
    }

    //labels
    public static Label createLabel(String text) {
        return new Label(text, UIStyles.getDefaultLabelStyle());
    }

    public static Label createLargeLabel(String text) {
        return new Label(text, UIStyles.getLargerLabelStyle());
    }

    //text fields
    public static TextField createTextField(String message) {
        TextField.TextFieldStyle style = UIStyles.getTextFieldStyle();

        TextField textField = new TextField("", style);
        textField.setMessageText(message);
        return textField;
    }

    public static NinePatchDrawable createDefaultBackgroundDrawable() {
        return new NinePatchDrawable(UIStyles.getDefaultBackgroundPatch());
    }

    public static NinePatchDrawable createDarkerBackgroundDrawable() {
        return new NinePatchDrawable(UIStyles.getDarkerBackgroundPatch());
    }
}
