package services.dummy;

import data.HorseType;
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
                new ShopResponse.HorseToBuy(HorseType.fromId(1), 500),
                new ShopResponse.HorseToBuy(HorseType.fromId(2), 750),
                new ShopResponse.HorseToBuy(HorseType.fromId(3), 600),
                new ShopResponse.HorseToBuy(HorseType.fromId(4), 1)
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
