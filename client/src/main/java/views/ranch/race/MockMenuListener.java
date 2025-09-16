package views.ranch.race;

import com.badlogic.gdx.Gdx;
import services.LobbyService;
import services.LobbyServiceImpl;
import services.managers.SessionManager;
import views.common.MenuContext;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

public class MockMenuListener implements RaceMenuPanel.RaceMenuListener {
    public LobbyService lobbyService = new LobbyServiceImpl();
    public String code;
    @Override
    public void onModeSelected(MenuContext.Mode mode) {
        if(mode.equals(MenuContext.Mode.SINGLEPLAYER)) {
            new Thread(()-> {
                code = lobbyService.createLobby();
                Gdx.app.log("Lobby", code);
            }).start();
        }
        else {
            lobbyService.joinLobby(code);
        }
    }
}
