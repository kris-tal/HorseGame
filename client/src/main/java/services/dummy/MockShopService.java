package services.dummy;

import data.PurchaseResult;
import data.ShopResponse;
import services.shop.ShopServiceImpl;

import java.util.List;

public class MockShopService extends ShopServiceImpl {

    public MockShopService() {
        super();
    }

    @Override
    public ShopResponse getShopData() {
        ShopResponse response = new ShopResponse();
        response.horses = List.of(
                );
        response.upgrades = List.of(

        );
        return response;
    }

    @Override
    public PurchaseResult postHorsePurchase(int horseId) {
        return new PurchaseResult(0, true, "Mock: Horse " + horseId + " purchased!");
    }

    @Override
    public PurchaseResult postUpgradePurchase(int horseId) {
        return new PurchaseResult(0, true, "Mock: Upgrade for horse " + horseId + " purchased!");
    }
}
