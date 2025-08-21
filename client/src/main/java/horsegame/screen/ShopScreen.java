package horsegame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import horsegame.data.PurchaseResult;
import horsegame.data.ShopItem;
import horsegame.data.ShopResponse;
import horsegame.service.*;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen extends ListScreen<ShopItem> {
    private final ShopServiceImpl shopService;
    private TextButton buyButton;
    private TextButton upgradeButton;

    public ShopScreen(Skin skin) {
        super(skin);
        this.shopService = new MockShopService();//no to jest mock service, bo nie miałam serwera
        createShopControls();
        refreshShop();
    }

    private void createShopControls() {
        buyButton = ButtonFactory.createTextButton("Buy", skin, Color.GREEN);
        upgradeButton = ButtonFactory.createTextButton("Upgrade", skin, Color.ORANGE);

        buyButton.setVisible(false);
        upgradeButton.setVisible(false);

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedItem != null) onBuyButtonClicked(selectedItem);
            }
        });

        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedItem != null) onUpgradeButtonClicked(selectedItem);
            }
        });

        mainTable.row();
        mainTable.add(buyButton).pad(10);
        mainTable.add(upgradeButton).pad(10);
    }

    @Override
    protected String getTitle() {
        return "HORSE SHOP";
    }

    @Override
    protected void populateRow(Table row, ShopItem item) {
        row.pad(5);
        row.setTouchable(Touchable.enabled);

        Color bg = item.isOwned() ? Color.LIGHT_GRAY : Color.WHITE;
        row.setBackground(skin.newDrawable("white", bg));

        row.add(new Label("ID: " + item.getId(), skin)).expandX().left().pad(10);
        row.add(new Label("Race: " + item.getRace(), skin)).expandX().left().pad(10);
        row.add(new Label("Price: " + item.getPrice(), skin)).expandX().left().pad(10);
    }

    @Override
    protected void onItemSelected(ShopItem item, Table row) {
        super.onItemSelected(item, row);
        buyButton.setVisible(!item.isOwned() && !item.isUpgrade());
        upgradeButton.setVisible(item.isOwned() || item.isUpgrade());
    }

    private void refreshShop() {
        new Thread(() -> {
            try {
                ShopResponse response = shopService.getShopData();
                List<ShopItem> allItems = new ArrayList<>();

                for (ShopResponse.HorseToBuy h : response.availableHorses)
                    allItems.add(new ShopItem(h.id, h.race, h.price, false, false));

                for (ShopResponse.Upgrade u : response.upgrades)
                    allItems.add(new ShopItem(u.horseId, u.race, u.price, true, true));

                Gdx.app.postRunnable(() -> populateList(allItems));
            } catch (Exception e) {
                Gdx.app.error("Shop", "Failed to fetch shop data", e);
            }
        }).start();
    }

    private void sendBuyHorseRequest(int horseId) {
        new Thread(() -> {
            PurchaseResult result = shopService.postHorsePurchase(horseId);
            Gdx.app.postRunnable(() -> {
                if (result.success) refreshShop();
                else Gdx.app.error("Shop", "Horse purchase failed: " + result.message);
            });
        }).start();
    }

    private void sendUpgradeRequest(int horseId) {
        new Thread(() -> {
            PurchaseResult result = shopService.postUpgradePurchase(horseId);
            Gdx.app.postRunnable(() -> {
                if (result.success) refreshShop();
                else Gdx.app.error("Shop", "Upgrade purchase failed: " + result.message);
            });
        }).start();
    }


    @Override
    protected void onEscapePressed() {
        Gdx.app.exit();
    }

    private static class ButtonFactory {
        static TextButton createTextButton(String text, Skin skin, Color color) {
            TextButton button = new TextButton(text, skin);
            button.getLabel().setColor(Color.BLACK);
            button.setColor(color);
            button.pad(10);
            return button;
        }
    }
    public void onBuyButtonClicked(ShopItem item) {
        if (!item.isOwned() && !item.isUpgrade()) {
            sendBuyHorseRequest(item.getId());
        }
    }

    public void onUpgradeButtonClicked(ShopItem item) {
        if (item.isOwned() || item.isUpgrade()) {
            sendUpgradeRequest(item.getId());
        }
    }

}
