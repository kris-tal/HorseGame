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
        response.availableHorses = List.of(
                new ShopResponse.HorseToBuy(1, "Turing Machine", 500),
                new ShopResponse.HorseToBuy(2, "Saw I (2004)", 750),
                new ShopResponse.HorseToBuy(3, "Piotr", 600),
                new ShopResponse.HorseToBuy(4, "Probability & Computing 2nd Edition", 1)
        );
        response.upgrades = List.of(

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
