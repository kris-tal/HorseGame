package views.ranch.race;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import views.common.MenuContext;
import views.common.Panel;
import views.common.UIFactory;

public class RaceMenuPanel extends Panel {
    public interface RaceMenuListener {
        void onModeSelected(MenuContext.Mode mode);
    }

    private RaceMenuListener listener;

    public RaceMenuPanel(float width, float height) {
        super(width, height);
        Label title = UIFactory.createLargeLabel("race menu");
        content.add(title).padBottom(bigPadding).center().row();

        TextButton singleplayerButton = UIFactory.createLargeTextButton("singleplayer");
        singleplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) listener.onModeSelected(MenuContext.Mode.SINGLEPLAYER);
            }
        });
        content.add(singleplayerButton).padBottom(defaultPadding).row();

        TextButton multiplayerButton = UIFactory.createLargeTextButton("multiplayer");
        multiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) listener.onModeSelected(MenuContext.Mode.MULTIPLAYER);
            }
        });
        content.add(multiplayerButton).padBottom(defaultPadding).row();
    }

    public void setListener(RaceMenuListener listener) {
        this.listener = listener;
    }

}
