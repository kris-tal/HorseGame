package views.ranch;

import com.badlogic.gdx.scenes.scene2d.Stage;
import views.common.Panel;
import views.common.ListPanel;

import java.util.ArrayList;
import java.util.List;

public class PanelManager {
    private final Stage stage;
    private final List<Panel> panels = new ArrayList<>();

    public PanelManager(Stage stage) {
        this.stage = stage;
    }

    public void registerPanel(Panel panel) {
        panels.add(panel);
        stage.addActor(panel);
        panel.hide(); // start hidden
    }

    public void showPanel(Panel panel) {
        hideAllPanels();
        panel.show(stage.getWidth(), stage.getHeight());

        if (panel instanceof ListPanel listPanel) {
            stage.setScrollFocus(listPanel.getScrollPane());
        }
    }

    public void hideAllPanels() {
        for (Panel panel : panels) {
            panel.hide();
        }
    }
}
