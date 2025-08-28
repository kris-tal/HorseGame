package views.ranch.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import data.PurchaseResult;
import data.ShopItem;
import data.ShopResponse;
import services.ShopServiceImpl;
import services.dummy.MockShopService;
import views.common.HorseFactory;
import views.common.ListPanel;
import views.common.UIFactory;

import java.util.ArrayList;
import java.util.List;

public class ShopPanel extends ListPanel<ShopItem> {

    private final ShopServiceImpl shopService;
    private TextButton buyButton;
    private TextButton upgradeButton;

    public ShopPanel(float width, float height) {
        super(width, height);
        this.shopService = new MockShopService();
        refreshShop();
    }

    private void createShopControls() {
        buyButton = UIFactory.createLargeTextButton("buy");
        upgradeButton = UIFactory.createLargeTextButton("upgrade");

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

        Stack buttonStack = new Stack();

        buttonStack.add(buyButton);
        buttonStack.add(upgradeButton);

        buyButton.setVisible(false);
        upgradeButton.setVisible(false);

        content.add(buttonStack).padTop(defaultPadding).center().row();
    }


    @Override
    protected String getTitle() {
        return "HORSE SHOP";
    }

    @Override
    protected void addContent(float width, float height) {
        super.addContent(width, height);
        createShopControls();
    }

    @Override
    protected void populateRow(Table row, ShopItem item) {
        row.pad(5);
        row.setTouchable(Touchable.enabled);

        if(item.isOwned()) row.setBackground(UIFactory.createDarkerBackgroundDrawable());
        else row.setBackground(UIFactory.createDefaultBackgroundDrawable());

        row.add(HorseFactory.createHorseImage("Boring Horse")).expandX().left().pad(10);
        row.add(UIFactory.createLabel("name: " + item.getId())).expandX().left().pad(10);
        row.add(UIFactory.createLabel("price: " + item.getPrice())).expandX();
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
