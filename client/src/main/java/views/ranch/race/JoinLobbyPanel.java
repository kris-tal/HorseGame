package views.ranch.race;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import views.common.ListPanel;
import views.common.MenuContext;
import views.common.UIFactory;

public class JoinLobbyPanel extends ListPanel {

    private LobbyMenuPanel.LobbyMenuListener listener;

    public JoinLobbyPanel(float width, float height) {
        super(width, height);
    }

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected void addContent(float width, float height) {
        TextField roomCodeField = UIFactory.createTextField("room code");
        content.add(roomCodeField).padTop(smallPadding).padBottom(defaultPadding).row();

        TextButton startButton = UIFactory.createLargeTextButton("join");
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //co sie stanie jak klikniesz join
            }
        });
        content.add(startButton).padTop(smallPadding).padBottom(defaultPadding).row();
    }

    @Override
    protected void populateRow(Table row, Object item) {

    }

}
