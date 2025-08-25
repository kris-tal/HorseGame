package views.ranch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.HorseGame;
import services.ResourceManager;
import views.common.UIFactory;
import views.common.UIStyles;

public class RanchStatusBar extends Table {
    public enum Action { RANKING, SHOP, RACE }

    public interface StatusBarListener {
        void onStatusBar(Action action);
    }
    private StatusBarListener listener;


    private final HorseGame game;
    private final ResourceManager resourceManager;

    public static class PlayerStatusItem extends Table {

        private final Label usernameLabel;
        private final Label moneyLabel;
        private final Label prestigeLabel;

        public PlayerStatusItem(String username, int money, int prestige) {
            super();

            setBackground(new NinePatchDrawable(UIStyles.getDefaultBackgroundPatch()));

            usernameLabel = UIFactory.createLabel("MajeczkaO4");

            TextureRegion coinRegion = ResourceManager.iconsAtlas.findRegion("coin_icon");
            Image coinImage = new Image(new TextureRegionDrawable(coinRegion));
            moneyLabel = new Label(String.valueOf(money), UIStyles.getDefaultLabelStyle());

            TextureRegion prestigeRegion = ResourceManager.iconsAtlas.findRegion("prestige_icon");
            Image prestigeImage = new Image(new TextureRegionDrawable(prestigeRegion));
            prestigeLabel = UIFactory.createLabel("prestige");

            //layout
            pad(5).padLeft(10).padRight(10); // padding inside the background

            add(usernameLabel).padRight(20).left();
            add(coinImage).size(32, 32).padRight(5);
            add(moneyLabel).padRight(20);
            add(prestigeImage).size(32, 32).padRight(5);
            add(prestigeLabel);

            setTransform(true);
            setOrigin(Align.center);
            setScale(1.5f);
        }

        public void updateMoney(int money) {
            moneyLabel.setText(String.valueOf(money));
        }

        public void updatePrestige(int prestige) {
            prestigeLabel.setText(String.valueOf(prestige));
        }
    }

    private final Skin skin;
    private final Table leftStack;
    private final Table rightStack;

    public RanchStatusBar(HorseGame game, ResourceManager resourceManager, Skin skin) {
        super(skin);
        this.game = game;
        this.resourceManager = resourceManager;

        this.skin = skin;

        this.leftStack = new Table(skin);
        this.rightStack = new Table(skin);

        setup();
    }

    private void setup() {
        setFillParent(true);
        top();
        padTop(40);
        padLeft(20);
        padRight(60);

        leftStack.left();
        rightStack.right();

        add(leftStack).expandX().fillX().left();
        add(rightStack).expandX().fillX().right();

        addItems();
    }

    private void addItems() {
        PlayerStatusItem playerStatusItem = new PlayerStatusItem("Majeczka04", 1, 99999);

        addToLeft(playerStatusItem);


        TextButton shopButton = UIFactory.createLargeTextButton("shop");
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) {
                    listener.onStatusBar(Action.SHOP);
                }
            }
        });

        TextButton raceButton = UIFactory.createLargeTextButton("race!");
        raceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) {
                    listener.onStatusBar(Action.RACE);
                }
            }
        });

        addToRight(shopButton);
        addToRight(raceButton);

    }

    private void addToLeft(Actor actor) {
        leftStack.add(actor).padRight(40).padLeft(10).padTop(10);
    }

    private void addToRight(Actor actor) {
        rightStack.add(actor).padLeft(60).padTop(-20);
    }

    public void setListener(StatusBarListener listener) {
        this.listener = listener;
    }
}
