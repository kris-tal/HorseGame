package views.ranch.info;

import animations.IdleHorseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import data.horse.HorseData;
import data.race.EquippedHorseService;
import views.common.Panel;
import views.common.UIFactory;

public class HorseInfoPanel extends Panel {
    private final HorseData horseData;

    public HorseInfoPanel(HorseData horseData) {
        super(400, 600);
        this.horseData = horseData;
        Gdx.app.log("HorseInfoPanel", horseData.getHorseType().getName());
        super.build();

    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void addContent(float width, float height) {
        Label nameLabel = UIFactory.createMediumLabel(horseData.getHorseType().getName());
        nameLabel.setWrap(true);
        nameLabel.setAlignment(Align.center);
        content.add(nameLabel).width(width - 2 * smallPadding).row();

        IdleHorseActor idleHorseActor = new IdleHorseActor(horseData.getHorseType(), 3);
        content.add(idleHorseActor).padTop(defaultPadding).row();

        Label levelLabel = UIFactory.createMediumLabel("level: " + horseData.getLevel());
        content.add(levelLabel).padTop(defaultPadding).row();

        Label trainingPointsLabel = UIFactory.createMediumLabel("training Points: " + horseData.getTrainingPoints());
        content.add(trainingPointsLabel).padTop(defaultPadding).row();

        final TextButton equipButton = UIFactory.createLargeTextButton("equip");

        if (EquippedHorseService.getInstance().isEquipped(horseData)) {
            equipButton.setText("equipped");
            equipButton.setDisabled(true);
            equipButton.setColor(0.7f, 0.7f, 0.7f, 1f);
        } else {
            equipButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedHorseService.getInstance().equipHorse(horseData);

                    equipButton.setText("equipped");
                    equipButton.setDisabled(true);
                    equipButton.setColor(0.7f, 0.7f, 0.7f, 1f);
                }
            });
        }

        content.add(equipButton).padTop(bigPadding).row();
    }
}
