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

    public void onRaceButtonClicked() {
        view.showPanel(null); // handled by RanchScreen
    }

    public void onShopButtonClicked() {
        view.openShopMenu();
    }

    public void onRankingButtonClicked() {
        view.openRanking();
    }

    public void onSingleplayerClicked(Panel horseSelectionPanel) {
        menuContext.setMode(MenuContext.Mode.SINGLEPLAYER);
        view.showPanel(horseSelectionPanel);
    }

    public void onMultiplayerClicked(Panel horseSelectionPanel) {
        menuContext.setMode(MenuContext.Mode.MULTIPLAYER);
        view.showPanel(horseSelectionPanel);
    }
}
