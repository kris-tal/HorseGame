package views.ranch.race;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import views.common.Panel;
import views.common.UIFactory;

public class LobbyPanel extends Panel {
    public LobbyPanel(float width, float height) {
        super(width, height);
    }

    @Override
    protected String getTitle() {
        return "waiting for other horse lovers..";
    }

    @Override
    protected void addContent(float width, float height) {

    }
}
