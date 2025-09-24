package views.ranch;

import views.common.Panel;
import views.common.MenuContext;
import views.ranch.race.JoinLobbyPanel;
import views.ranch.race.WaitingLobbyPanel;

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

    public void onSingleplayerClicked() {
        menuContext.setRaceMode(MenuContext.RaceMode.SINGLEPLAYER);
        view.hideAllPanels();
        view.navigateToRace();
    }

    public void onMultiplayerClicked(Panel panel) {
        menuContext.setRaceMode(MenuContext.RaceMode.MULTIPLAYER);
        view.hideAllPanels();
        view.showPanel(panel);
    }

    public void onCreateLobbyClicked(WaitingLobbyPanel panel) {
        panel.refresh();
        menuContext.setLobbyMode(MenuContext.LobbyMode.CREATE);
        view.hideAllPanels();
        view.showPanel(panel);
    }

    public void onJoinLobbyClicked(JoinLobbyPanel panel) {
        menuContext.setLobbyMode(MenuContext.LobbyMode.JOIN);
        view.hideAllPanels();
        view.showPanel(panel);
    }

    public void onExitPressed() {
        view.showExitConfirmation();
    }
}
