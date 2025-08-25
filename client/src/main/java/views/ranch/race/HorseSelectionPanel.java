package views.ranch.race;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import views.common.ListPanel;
import views.common.UIFactory;

public class HorseSelectionPanel extends ListPanel {
    public HorseSelectionPanel(float width, float height) {
        super(width, height);

        Label title = UIFactory.createLargeLabel("select your horse");
        content.add(title).padBottom(bigPadding).center().row();
    }

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected void populateRow(Table row, Object item) {

    }
}
