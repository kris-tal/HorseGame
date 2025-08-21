package horsegame.service;

import horsegame.data.PurchaseResult;
import java.util.List;
import horsegame.data.ShopResponse;
//generalnie to nie mialam serwera a chcialam sprawdzicn czy to jakkolwiek działa więc se dodałam taką klasę
public class MockShopService extends ShopServiceImpl {

    public MockShopService() {
        super("mock-token");
    }

    @Override
    public ShopResponse getShopData() {
        ShopResponse response = new ShopResponse();
        response.availableHorses = List.of(
                new ShopResponse.HorseToBuy(1, "Arabian", 500),
                new ShopResponse.HorseToBuy(2, "Friesian", 750)
        );
        response.upgrades = List.of(
                new ShopResponse.Upgrade(1, "Arabian", 200),
                new ShopResponse.Upgrade(2, "Friesian", 300)
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
