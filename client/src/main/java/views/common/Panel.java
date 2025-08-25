package views.common;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public abstract class Panel extends Table {
    protected float smallPadding = 20f;
    protected float defaultPadding = 50f;
    protected float bigPadding = 70f;
    protected Table content = new Table();

    public Panel(float width, float height) {
        super();

        setBackground(new NinePatchDrawable(UIStyles.getDefaultBackgroundPatch()));
        setSize(width, height);
        pad(smallPadding);

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
