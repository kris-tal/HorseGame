package horsegame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import horsegame.screen.ScreenID;

public class StatusBar extends Table {
    public enum Type { FULL, LIGHT }
    public enum Action { INVENTORY, MAP, BUILDING_MODE, RACE }

    public interface StatusBarListener {
        void onStatusBar(Action action);
    }
    private StatusBarListener listener;

    public static class PlayerStatusItem extends Table {
        private Label usernameLabel;
        private Label prestigeLabel;
        private Label moneyLabel;

        public PlayerStatusItem(Skin skin) {
            super(skin);
            setup();
        }

        private void setup() {
            usernameLabel = new Label("Username", getSkin());
            prestigeLabel = new Label("Prestige", getSkin());
            moneyLabel = new Label("Money", getSkin());

            add(usernameLabel).padRight(10);
            add(prestigeLabel).padRight(10);
            add(moneyLabel);

            setBackground(getSkin().getDrawable("default-round"));      //temporary
        }

        //no generalnie potrzebuje jakiejs logiki ktora bedzie mi pobierala te rzeczy z bazy danych
        //i odpowiednio je updatowala
        //ale na razie niech to jest po prostu tak

    }

    private final Type type;
    private final ScreenID screenID;
    private final Skin skin;
    private final Table leftStack;
    private final Table rightStack;

    public StatusBar(Skin skin, Type type, ScreenID screenID) {
        super(skin);

        this.skin = skin;
        this.type = type;
        this.screenID = screenID;

        this.leftStack = new Table(skin);
        this.rightStack = new Table(skin);

        setup();
    }

    private void setup() {
        setFillParent(true);
        top();
        pad(20);

        leftStack.left();
        rightStack.right();

        add(leftStack).expandX().fillX().left();
        add(rightStack).expandX().fillX().right();

        addItems();
        debug();
    }

    private void addItems() {
        PlayerStatusItem playerStatusItem = new PlayerStatusItem(getSkin());
        TextButton inventoryButton = new TextButton("inv", getSkin());
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Inventory button clicked");
            }
        });

        if (type == Type.FULL) {
            TextButton buildingModeButton = new TextButton("buildingMode", getSkin());
            buildingModeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Building mode button clicked");
                }
            });

            TextButton raceButton = new TextButton("race", getSkin());
            raceButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Race button clicked");
                }
            });

            addToRight(buildingModeButton);
            addToRight(raceButton);
        }

        TextButton mapButton = new TextButton("map", getSkin());
        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMapMenu();
            }
        });

        addToLeft(playerStatusItem);
        addToLeft(inventoryButton);

        addToRight(mapButton);
    }

    private void addToLeft(Actor actor) {
        leftStack.add(actor).padRight(10);
    }

    private void addToRight(Actor actor) {
        rightStack.add(actor).padLeft(10);
    }

    //BUTTON LOGIC
    private void showMapMenu() {
        Gdx.app.log("DEBUG", "Showing map menu");

        Table menu = new Table(skin);


    }
}
