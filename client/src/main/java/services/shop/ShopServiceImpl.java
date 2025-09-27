package services.shop;

import com.google.gson.Gson;
import data.shop.PurchaseResult;
import data.shop.ShopResponse;
import services.managers.SessionManagerPort;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ShopServiceImpl implements ShopService {

    private final Gson gson;
    private final SessionManagerPort sessionManager;

    public ShopServiceImpl(SessionManagerPort sessionManager) {
        this.gson = new Gson();
        this.sessionManager = sessionManager;
    }

    @Override
    public ShopResponse getShopData() {
        HttpRequest request = sessionManager.getProtectedRequestsService()
                .ProtectedGet("/shop")
                .build();

        try {
            HttpResponse<String> response = sessionManager.getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), ShopResponse.class);
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }

    @Override
    public PurchaseResult postHorsePurchase(int horseId) {
        PurchaseResult result = sendPurchaseRequest(horseId);
        return new PurchaseResult(result.money, result.success, result.message, false);
    }

    @Override
    public PurchaseResult postUpgradePurchase(int horseId) {
        PurchaseResult result = sendPurchaseRequest(horseId);
        return new PurchaseResult(result.money, result.success, result.message, true);
    }

    private PurchaseResult sendPurchaseRequest(int horseId) {
        String requestBody = gson.toJson(Map.of("id", horseId));

        HttpRequest request = sessionManager.getProtectedRequestsService()
                .ProtectedPost("/shop",
                        HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = sessionManager.getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            int status = response.statusCode();
            if (status == 200 || status == 400 || status == 401 || status == 404) {
                return gson.fromJson(response.body(), PurchaseResult.class);
            }
        } catch (IOException | InterruptedException e) {
            return new PurchaseResult(0, false, "Request failed: " + e.getMessage());
        }
        return new PurchaseResult(0, false, "Unknown error");
    }
}
