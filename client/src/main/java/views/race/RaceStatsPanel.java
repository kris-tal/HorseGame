package views.race;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import views.common.Panel;
import services.managers.ResourceManager;

public class RaceStatsPanel extends Panel {
    private Label distanceLabel;
    private Label coinsLabel;

    public RaceStatsPanel(float width, float height) {
        super(width, height);
    }

    @Override
    protected String getTitle() {
        return "Race Results";
    }

    @Override
    protected void addContent(float width, float height) {
        distanceLabel = new Label("Distance: 0m", ResourceManager.uiSkin);
        coinsLabel = new Label("Coins: 0", ResourceManager.uiSkin);

        content.add(distanceLabel).padBottom(defaultPadding).row();
        content.add(coinsLabel).padBottom(defaultPadding).row();
    }

    public void updateStats(int distance, int coins) {
        distanceLabel.setText("Distance: " + distance + "m");
        coinsLabel.setText("Coins: " + coins);
    }
}
