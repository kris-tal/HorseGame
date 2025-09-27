package views.race.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import data.lobby.LobbyInfo;
import data.lobby.PlayerInfoClient;
import services.listeners.WaitingLobbyListener;
import services.managers.ConnectionManager;
import services.managers.LobbyManager;
import views.common.ui.ListPanel;
import views.common.ui.UIFactory;

import java.util.ArrayList;
import java.util.List;

public class WaitingLobbyPanel extends ListPanel<PlayerInfoClient> implements LobbyManager.LobbyStateListener {
    private LobbyManager lobbyManager;
    private TextButton startButton;
    private Label playerCountLabel;
    private WaitingLobbyListener listener;
    private Runnable onShowCallback;

    private boolean isCreatingLobby = false;

    public WaitingLobbyPanel(float width, float height, LobbyManager lobbyManager) {
        super(width, height);
        this.lobbyManager = lobbyManager;
        this.lobbyManager.setStateListener(this);
    }

    public void createLobby() {
        if (isCreatingLobby) {
            return;
        }

        isCreatingLobby = true;

        new Thread(() -> {
            this.lobbyManager.createLobby();
            com.badlogic.gdx.Gdx.app.postRunnable(() -> {
                isCreatingLobby = false;
            });
        }).start();
    }

    /* Highlight lobby code */
    @Override
    protected String getTitle() {
        if(lobbyManager == null) return "[NULL]";
        LobbyInfo lobby = this.lobbyManager.getCurrentLobby();
        if (lobby == null) {
            return "[NULL]";
        }
        String title = "LOBBY: " + lobby.getLobbyCode();
        return title;
    }
    @Override
    protected void addContent(float width, float height) {
        listTable = new Table();
        listTable.top();

        scrollPane = new ScrollPane(listTable);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        float scrollWidth = width * 0.8f;
        float scrollHeight = height * 0.5f;
        content.add(scrollPane).width(scrollWidth).height(scrollHeight).center();

        TextButton leaveButton = UIFactory.createTextButton("Leave Lobby");
        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lobbyManager.leaveLobby();
                if (listener != null) {
                    listener.onLeaveLobby();
                }
            }
        });
        content.add(leaveButton).padBottom(defaultPadding).row();
        if(lobbyManager != null) {
            LobbyInfo lobby = lobbyManager.getCurrentLobby();
            if (lobby != null) {
                playerCountLabel = UIFactory.createLabel("Players: " + lobby.getPlayerCount());
                content.add(playerCountLabel).padTop(smallPadding).padBottom(defaultPadding).row();
            }
        }

        startButton = UIFactory.createLargeTextButton("START RACE!");
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lobbyManager.startLobby();
            }
        });

        content.add(startButton).padTop(smallPadding).row();
    }

    @Override
    protected void populateRow(Table row, PlayerInfoClient player) {
        if (player == null) {
            return;
        }
        Gdx.app.log("Lobby", "Populating " + player.toString());
        Label nameLabel = UIFactory.createLabel(player.getUsername());
        row.add(nameLabel).expandX().left().padRight(10f);

        if (player.isCreator()) {
            Label creatorLabel = UIFactory.createLabel("HOST");
            creatorLabel.setColor(1f, 0.8f, 0.2f, 1f); // Gold color
            row.add(creatorLabel).right().padRight(10f);
        }
    }

    public void updateStartButtonVisibility() {
        if (startButton != null) {
            boolean isCreator = lobbyManager.isCurrentUserCreator();
            startButton.setVisible(isCreator);
            startButton.setColor(isCreator ? 1f : 0.5f, isCreator ? 1f : 0.5f, isCreator ? 1f : 0.5f, 1f);
        }
    }


    public void refresh() {
        build();
    }

    public void setListener(WaitingLobbyListener listener) {
        this.listener = listener;
    }
    
    public void setOnShowCallback(Runnable callback) {
        this.onShowCallback = callback;
    }

    @Override
    public void onLobbyStarted() {
        Gdx.app.postRunnable(() -> {
            if (listener != null) {
                listener.onLobbyStarted();
            }
        });
    }

    @Override
    public void onLobbyClosed() {
        Gdx.app.postRunnable(this::hide);
    }

    @Override
    public void onLobbyInitialized() {
        LobbyInfo lobby = lobbyManager.getCurrentLobby();
        if (lobby != null) {
            if (playerCountLabel == null) {
                playerCountLabel = UIFactory.createLabel("Players: " + lobby.getPlayerCount());
                content.add(playerCountLabel).padBottom(defaultPadding).row();
            } else {
                playerCountLabel.setText("Players: " + lobby.getPlayerCount());
            }
            if (titleLabel != null) {
                titleLabel.setText("LOBBY: " + lobby.getLobbyCode());
            }
        }

        if (onShowCallback != null) {
            onShowCallback.run();
        }
        updateStartButtonVisibility();
    }

    /* It is invoked each time a player enters or exits */
    @Override
    public void onPlayersUpdated() {
        LobbyInfo lobby = lobbyManager.getCurrentLobby();
        if (lobby != null) {
            List<PlayerInfoClient> playerList = new ArrayList<>(lobby.getPlayers().values());
            populateList(playerList);   // only update rows
            updateStartButtonVisibility();
            if (playerCountLabel != null) {
                playerCountLabel.setText("Players: " + lobby.getPlayerCount());
            }
        }
    }


    @Override
    public void close() {
        lobbyManager.leaveLobby();
        hide();
    }

}
