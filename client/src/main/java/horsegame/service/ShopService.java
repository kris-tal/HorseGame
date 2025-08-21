package horsegame.service;

import horsegame.data.PurchaseResult;
import horsegame.data.ShopResponse;

public interface ShopService {
    ShopResponse getShopData();
    PurchaseResult postHorsePurchase(int horseId);
    PurchaseResult postUpgradePurchase(int horseId);
    String getAuthToken();
    void setAuthToken(String authToken);
}
