package services;

import data.PurchaseResult;
import data.ShopResponse;

public interface ShopService {
    ShopResponse getShopData();
    PurchaseResult postHorsePurchase(int horseId);
    PurchaseResult postUpgradePurchase(int horseId);
    String getAuthToken();
    void setAuthToken(String authToken);
}
