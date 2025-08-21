package horsegame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import horsegame.data.HorseData;
import horsegame.service.HorseService;

import java.util.List;

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
