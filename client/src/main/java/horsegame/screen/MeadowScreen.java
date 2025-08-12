package horsegame.screen;

import horsegame.HorseGame;
import manager.ResourceManager;

public class MeadowScreen extends RoamScreen {

    public MeadowScreen(HorseGame game, ResourceManager resourceManager) {
        super(game, resourceManager);
    }

    @Override
    public void show() {
        super.show();
        showStatusBar(ScreenID.MEADOW);
    }
}
