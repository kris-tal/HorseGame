package views.ranch;

import animations.StrollingHorseActor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.HorseGame;
import data.horse.HorseData;
import data.lobby.LobbyInfo;
import services.horse.HorseService;
import services.horse.HorseServiceImpl;
import services.listeners.JoinLobbyListener;
import services.listeners.WaitingLobbyListener;
import services.managers.*;
import views.common.ui.ConfirmationPanel;
import views.common.context.MenuContext;
import views.common.ui.Panel;
import views.race.multiplayer.RaceMultiplayerScreen;
import views.race.menu.*;
import views.ranch.shop.ShopPanel;
import views.ranch.info.HorseInfoPanel;

import java.util.List;

public class RanchScreen implements Screen, RanchView, JoinLobbyListener {
    /* Lobby instance*/
    private final LobbyManager lobbyManager;
    private final HorseGame game;
    private final RanchPresenter presenter;
    private final MenuContext menuContext;
    private Stage stage;
    private PanelManager panelManager;

    private Array<StrollingHorseActor> horses = new Array<>();
    private final java.util.Map<Integer, com.badlogic.gdx.math.Vector2> horseIdToPosition = new java.util.HashMap<>();
    private Rectangle fence;

    private RaceMenuPanel raceMenuPanel;
    private LobbyMenuPanel lobbyMenuPanel;
    private WaitingLobbyPanel createLobbyPanel;
    private JoinLobbyPanel joinLobbyPanel;
    private ShopPanel shopPanel;
    private HorseInfoPanel horseInfoPanel;
    private ConfirmationPanel exitPanel;

    private RanchStatusBar statusBar;

    public RanchScreen(HorseGame game) {
        this.game = game;
        this.menuContext = new MenuContext();
        this.presenter = new RanchPresenter(this, menuContext);
        this.lobbyManager =
                new LobbyManager(SessionManager.getInstance(), ConnectionManager.getInstance());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        panelManager = new PanelManager(stage);

        setupBackground();
        setupHorses();
        setupPanels();
        setupStatusBar();
    }

    private void setupBackground() {
        Texture bgTexture = ResourceManager.ranchScreenBgTexture;
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
        background.toBack();
    }

    private void setupHorses() {
        fence = new Rectangle(100, 100, 1550, 600);

        HorseService horseService = new HorseServiceImpl();
        List<HorseData> ownedHorses = horseService.getUserHorses();

        for (HorseData horse : ownedHorses) {
            StrollingHorseActor actor = new StrollingHorseActor(
                    horse, 3,
                    fence.x, fence.x + fence.width,
                    fence.y, fence.y + fence.height
            );
            actor.addListener(new HorseClickListener(horse));
            com.badlogic.gdx.math.Vector2 saved = horseIdToPosition.get(horse.getId());
            if (saved != null) {
                actor.setPosition(saved.x, saved.y);
            } else {
                actor.setPosition(
                        MathUtils.random(fence.x, fence.x + fence.width),
                        MathUtils.random(fence.y, fence.y + fence.height)
                );
            }
            horses.add(actor);
            stage.addActor(actor);
        }
    }

    private void addNewHorse(HorseData horse) {
        StrollingHorseActor actor = new StrollingHorseActor(
                horse, 3,
                fence.x, fence.x + fence.width,
                fence.y, fence.y + fence.height
        );
        actor.addListener(new HorseClickListener(horse));
        
        float x = fence.x + 50; 
        float y = fence.y + 50; 
        actor.setPosition(x, y);
        
        horses.add(actor);
        stage.addActor(actor);
    }

    private void addNewHorseFromServer() {
        HorseService horseService = new HorseServiceImpl();
        List<HorseData> allHorses = horseService.getUserHorses();
        
        java.util.Set<Integer> existingHorseIds = new java.util.HashSet<>();
        for (StrollingHorseActor actor : horses) {
            existingHorseIds.add(actor.getHorseData().getId());
        }
        
        for (HorseData horse : allHorses) {
            if (!existingHorseIds.contains(horse.getId())) {
                addNewHorse(horse);
            }
        }
    }

    private void setupPanels() {
        float panelWidth = Gdx.graphics.getWidth() / 2f;
        float panelHeight = Gdx.graphics.getHeight() / 2f;

        raceMenuPanel = new RaceMenuPanel(panelWidth, panelHeight);
        lobbyMenuPanel = new LobbyMenuPanel(panelWidth, panelHeight);
        createLobbyPanel = new WaitingLobbyPanel(panelWidth, panelHeight, lobbyManager);
        joinLobbyPanel = new JoinLobbyPanel(panelWidth, panelHeight, lobbyManager);
        shopPanel = new ShopPanel(panelWidth, panelHeight);

        panelManager.registerPanel(raceMenuPanel);
        panelManager.registerPanel(lobbyMenuPanel);
        panelManager.registerPanel(createLobbyPanel);
        panelManager.registerPanel(joinLobbyPanel);
        panelManager.registerPanel(shopPanel);

        raceMenuPanel.setListener(mode -> {
            switch (mode) {
                case SINGLEPLAYER -> presenter.onSingleplayerClicked();
                case MULTIPLAYER -> presenter.onMultiplayerClicked(lobbyMenuPanel);
            }
        });

        lobbyMenuPanel.setListener(mode -> {
            switch (mode) {
                case CREATE -> presenter.onCreateLobbyClicked(createLobbyPanel);
                case JOIN -> presenter.onJoinLobbyClicked(joinLobbyPanel);
            }
        });

        joinLobbyPanel.setListener(this);
        
        createLobbyPanel.setListener(new WaitingLobbyListener() {
            @Override
            public void onLobbyStarted() {
                /* Handle lobby started */
                navigateToRace();
            }
            
            @Override
            public void onLeaveLobby() {
                hideAllPanels();
            }
        });
        
        createLobbyPanel.setOnShowCallback(() -> {
            showPanel(createLobbyPanel);
        });

        shopPanel.setOnPurchaseComplete(result -> {
            statusBar.updateMoney(result.money);
            if (!result.isUpgrade) {
                addNewHorseFromServer();
            }
        });
    }

    private void setupStatusBar() {
        Skin skin = ResourceManager.uiSkin;
        statusBar = new RanchStatusBar(game, skin);
        stage.addActor(statusBar);

        statusBar.setListener(action -> {
            switch (action) {
                case SHOP -> presenter.onShopButtonClicked(shopPanel);
                case RACE -> presenter.onRaceButtonClicked(raceMenuPanel);
            }
        });
    }

    private class HorseClickListener extends ClickListener {
        private final HorseData horse;

        public HorseClickListener(HorseData horse) {
            this.horse = horse;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            horseInfoPanel = new HorseInfoPanel(horse);
            panelManager.registerPanel(horseInfoPanel);
            showPanel(horseInfoPanel);
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            showExitConfirmation();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            game.showTrainingScreen();
        }
        stage.act(delta);
        stage.draw();

        horses.sort((a, b) -> Float.compare(b.getY(), a.getY()));
        for (int i = 0; i < horses.size; i++) {
            horses.get(i).setZIndex(i + 1); // Keep horses at low z-index (1-10)
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void showPanel(Panel panel) {
        panelManager.showPanel(panel);
    }

    @Override
    public void hideAllPanels() {
        panelManager.hideAllPanels();
    }

    @Override
    public void navigateToRace() {
        switch (menuContext.getRaceMode()) {
            case SINGLEPLAYER -> game.showTrainingScreen();
            case MULTIPLAYER -> {
                GameManager gameManager = new GameManager(SessionManager.getInstance(), ConnectionManager.getInstance(), lobbyManager.getCurrentLobby());
                game.setScreen(new views.race.multiplayer.RaceMultiplayerScreen(game, gameManager));
            }
        }
    }

    @Override
    public void onLobbyJoined() {
        presenter.onCreateLobbyClicked(createLobbyPanel);
    }

    @Override
    public void showExitConfirmation() {
        if (exitPanel == null) {
            exitPanel = ConfirmationPanel.getInstance(400, 200, "exit?", Gdx.app::exit);
            panelManager.registerPanel(exitPanel);
        }
        panelManager.showPanel(exitPanel);
    }

}
