package views.ranch.info;

import data.horse.HorseData;
import views.common.Panel;

public class HorseInfoPanel extends Panel {
    private final HorseData horseData;

    public HorseInfoPanel(HorseData horseData) {
        super(400, 600, horseData.getHorseType().getName());
        this.horseData = horseData;
    }

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected void addContent(float width, float height) {

    }
}
