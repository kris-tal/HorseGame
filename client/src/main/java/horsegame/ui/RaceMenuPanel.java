package horsegame.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class RaceMenuPanel extends Table {

    public RaceMenuPanel(float width, float height) {
        super();

        float defaultPadding = 50f;

        setBackground(new NinePatchDrawable(UIStyles.getDefaultBackgroundPatch()));

        setSize(width, height);
        pad(10);

        add(new Label("race menu", UIStyles.getLargerLabelStyle())).padBottom(defaultPadding).row();
        TextButton singleplayerButton = UIFactory.createLargeTextButton("singleplayer");
        singleplayerButton.addListener(new ClickListener() {

        });
        add(singleplayerButton).padBottom(defaultPadding).row();


        TextButton multiplayerButton = UIFactory.createLargeTextButton("multiplayer");
        multiplayerButton.addListener(new ClickListener() {

        });
        add(multiplayerButton).padBottom(defaultPadding).row();

        setVisible(false);
    }

    public void show(float screenWidth, float screenHeight) {
        setPosition(
                (screenWidth - getWidth()) / 2f,
                (screenHeight - getHeight()) / 2f
        );
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    public boolean isOpen() {
        return isVisible();
    }
}
