package views.ranch.race;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import views.common.Panel;
import views.common.UIFactory;

public class LobbyPanel extends Panel {
    public LobbyPanel(float width, float height) {
        super(width, height);

        Label title = UIFactory.createLargeLabel("waiting for other horse lovers..");
        content.add(title).padBottom(bigPadding).center().row();
    }
}
