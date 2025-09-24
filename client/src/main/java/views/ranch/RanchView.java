package views.ranch;

import views.common.ui.Panel;

public interface RanchView {
    void showPanel(Panel panel);

    void hideAllPanels();

    void navigateToRace();

    void showExitConfirmation();
}
