package horsegame.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class RaceMenuPanel extends Table {

    public RaceMenuPanel(float width, float height) {
        super();

        float smallPadding = 20f;
        float defaultPadding = 50f;
        float bigPadding = 70f;

        setBackground(new NinePatchDrawable(UIStyles.getDefaultBackgroundPatch()));
        setSize(width, height);
        pad(smallPadding);

        Table content = new Table();

        Label title = UIFactory.createLargeLabel("race menu");
        content.add(title).padBottom(bigPadding).center().row();

        TextButton singleplayerButton = UIFactory.createLargeTextButton("singleplayer");
        singleplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        content.add(singleplayerButton).padBottom(defaultPadding).row();

        TextButton multiplayerButton = UIFactory.createLargeTextButton("multiplayer");
        multiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //open multiplayer
            }
        });
        content.add(multiplayerButton).padBottom(defaultPadding).row();

        TextButton closeButton = UIFactory.createLargeTextButton("X");
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        Container<TextButton> closeContainer = new Container<>(closeButton);
        closeContainer.top().right().padTop(2 * smallPadding).padRight(2 * smallPadding);

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.add(content);
        stack.add(closeContainer);

        add(stack).expand().fill();

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
