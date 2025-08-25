package horsegame.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import horsegame.data.HorseData;

public class HorseSelectionScreen extends ListScreen<HorseData> {
    public HorseSelectionScreen(Skin skin) {
        super(skin);
    }

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected void populateRow(Table row, HorseData item) {

    }

}
