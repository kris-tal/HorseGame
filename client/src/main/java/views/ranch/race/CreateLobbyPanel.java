package views.ranch.race;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import data.horse.HorseType;
import views.common.ListPanel;
import views.common.MenuContext;
import views.common.Panel;
import views.common.UIFactory;

public class CreateLobbyPanel extends ListPanel {       //odpowiedni parametr<> tzreba ig zrobic klase RacePlayer czy cos

    public CreateLobbyPanel(float width, float height) {
        super(width, height);
    }

    @Override
    protected String getTitle() {
        return "ROOM CODE";    //tutaj dajesz funkcje zwracajaca id twojego lobby (najlepiej all caps)
    }

    @Override
    protected void addContent(float width, float height) {

        TextButton startButton = UIFactory.createLargeTextButton("start!");
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //co sie stanie jak klikniesz start
            }
        });
        content.add(startButton).padTop(smallPadding).padBottom(defaultPadding).row();
    }

    @Override
    protected void populateRow(Table row, Object item) {

    }
}
