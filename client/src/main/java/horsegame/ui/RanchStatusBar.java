package horsegame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RanchStatusBar extends Table {
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
            usernameLabel = new Label("Username", UIStyles.getDefaultLabelStyle());
            prestigeLabel = new Label("Prestige", UIStyles.getDefaultLabelStyle());
            moneyLabel = new Label("Money", UIStyles.getDefaultLabelStyle());

            add(usernameLabel).padRight(10);
            add(prestigeLabel).padRight(10);
            add(moneyLabel);

            setBackground(getSkin().getDrawable("default-round"));      //temporary
        }

        //no generalnie potrzebuje jakiejs logiki ktora bedzie mi pobierala te rzeczy z bazy danych
        //i odpowiednio je updatowala
        //ale na razie niech to jest po prostu tak

    }

    private final Skin skin;
    private final Table leftStack;
    private final Table rightStack;

    public RanchStatusBar(Skin skin) {
        super(skin);

        this.skin = skin;

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

        TextButton shopButton = new TextButton("shop", UIStyles.getDefaultButtonStyle());
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMapMenu();
            }
        });

        TextButton raceButton = new TextButton("race!", UIStyles.getDefaultButtonStyle());
        raceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Race button clicked");
            }
        });

        addToLeft(playerStatusItem);

        addToRight(shopButton);
        addToRight(raceButton);

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
