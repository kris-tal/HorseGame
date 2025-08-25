package horsegame.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LobbyPanel extends Panel{
    public LobbyPanel(float width, float height) {
        super(width, height);

        Label title = UIFactory.createLargeLabel("waiting for other horse lovers..");
        content.add(title).padBottom(bigPadding).center().row();
    }
}
