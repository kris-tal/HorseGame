package views.ranch;


import com.badlogic.gdx.Screen;
import views.common.Panel;

public interface RanchView {
    void showPanel(Panel panel);

    void hideAllPanels();

    void navigateToRace();
}
