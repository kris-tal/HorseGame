package views.ranch;

import views.common.ui.Panel;
import views.common.context.MenuContext;
import views.race.menu.WaitingLobbyPanel;
import views.race.menu.JoinLobbyPanel;

/*
    TODO:
    * Change LobbyMode.CREATE to LobbyMode.WAITING
    * Make the steering for lobby switching be outside of the WAITING section
    * Make WAITING
 */

public class RanchPresenter {
    private final RanchView view;
    private final MenuContext menuContext;
    private boolean isCreatingLobby = false;

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
        if (isCreatingLobby) {
            return;
        }
        
        isCreatingLobby = true;

        new Thread(() -> {
            services.managers.LobbyManager.getInstance().createLobby();
            com.badlogic.gdx.Gdx.app.postRunnable(() -> {
                isCreatingLobby = false;
            });
        }).start();
        
        menuContext.setLobbyMode(MenuContext.LobbyMode.CREATE);
        view.hideAllPanels();
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
