package services.dummy;

import data.PurchaseResult;
import data.ShopResponse;
import services.ShopServiceImpl;

import java.util.List;

public class MockShopService extends ShopServiceImpl {

    public MockShopService() {
        super();
    }

    @Override
    public ShopResponse getShopData() {
        ShopResponse response = new ShopResponse();
        response.horses = List.of(
                new ShopResponse.HorseToBuy("Arabian", 500),
                new ShopResponse.HorseToBuy("Friesian", 750),
                new ShopResponse.HorseToBuy("Mustang", 600)
        );
        response.upgrades = List.of(
                new ShopResponse.Upgrade("Arabian",  200),
                new ShopResponse.Upgrade("Friesian",  300)
        );
        return response;
    }

    @Override
    public PurchaseResult postHorsePurchase(int horseId) {
        return new PurchaseResult(true, "Mock: Horse " + horseId + " purchased!");
    }

    @Override
    public PurchaseResult postUpgradePurchase(int horseId) {
        return new PurchaseResult(true, "Mock: Upgrade for horse " + horseId + " purchased!");
    }
}
