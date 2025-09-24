package views.ranch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import views.common.ListPanel;
import views.common.Panel;

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
        panel.hide();
    }

    public void showPanel(Panel panel) {
        hideAll();
        if (!stage.getActors().contains(panel, true)) {
            stage.addActor(panel);
        }
        panel.show(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (panel instanceof ListPanel listPanel) {
            stage.setScrollFocus(listPanel.getScrollPane());
        }
    }

    public void hideAll() {
        for (Panel panel : panels) {
            panel.hide();
        }
    }
}
