package views.common;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

        Label title = UIFactory.createLargeLabel(getTitle());
        content.add(title).padBottom(defaultPadding).center().row();

        addContent(width, height);

        Container<Table> contentContainer = new Container<>(content);

        add(contentContainer).expand().fill();

        TextButton closeButton = UIFactory.createLargeTextButton("X");
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        Container<TextButton> closeContainer = new Container<>(closeButton);
        closeContainer.top().right();

        addActor(closeButton);
        closeButton.setPosition(getWidth() - closeButton.getWidth() - smallPadding, getHeight() - closeButton.getHeight() - smallPadding);

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

    protected abstract String getTitle();

    protected abstract void addContent(float width, float height);

    public boolean isOpen() {
        return isVisible();
    }
}
