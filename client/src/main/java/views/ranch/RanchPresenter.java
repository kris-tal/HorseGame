package views.ranch;

import views.common.MenuContext;
import views.common.Panel;

public class RanchPresenter {
    private final RanchView view;
    private final MenuContext menuContext;

    public RanchPresenter(RanchView view, MenuContext menuContext) {
        this.view = view;
        this.menuContext = menuContext;
    }

    public void onRaceButtonClicked(Panel panel) {
        view.hideAllPanels();
        view.showPanel(panel);
    }

    public void onShopButtonClicked(Panel panel) {
        view.hideAllPanels();
        view.showPanel(panel);
    }

    public void onRankingButtonClicked(Panel panel) {
        view.hideAllPanels();
        view.showPanel(panel);
    }

    public void onSingleplayerClicked() {
        menuContext.setMode(MenuContext.RaceMode.SINGLEPLAYER);
        view.hideAllPanels();
        view.navigateToRace();
    }

    public void onMultiplayerClicked(Panel panel) {
        menuContext.setMode(MenuContext.RaceMode.MULTIPLAYER);
        view.hideAllPanels();
        view.showPanel(panel);
    }
}
