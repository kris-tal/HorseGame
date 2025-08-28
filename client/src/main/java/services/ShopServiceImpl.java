package services;

import com.google.gson.Gson;
import data.PurchaseResult;
import data.ShopResponse;
import services.managers.SessionManager;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ShopServiceImpl implements ShopService {

    private final Gson gson;

    public ShopServiceImpl() {
        this.gson = new Gson();
    }

    @Override
    public ShopResponse getShopData() {
        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService()
                .ProtectedGet("/shop")
                .build();

        try {
            HttpResponse<String> response = SessionManager.getInstance()
                    .getProtectedRequestsService()
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
        return sendPurchaseRequest("/shop/horse", horseId);
    }

    @Override
    public PurchaseResult postUpgradePurchase(int horseId) {
        return sendPurchaseRequest("/shop/upgrade", horseId);
    }

    private PurchaseResult sendPurchaseRequest(String endpoint, int horseId) {
        String requestBody = gson.toJson(Map.of("horseId", horseId));

        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService()
                .ProtectedPost(endpoint,
                        HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = SessionManager.getInstance()
                    .getProtectedRequestsService()
                    .GetHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), PurchaseResult.class);
            } else if (response.statusCode() == 400) {
                return new PurchaseResult(false, "Invalid request data");
            } else if (response.statusCode() == 401) {
                return new PurchaseResult(false, "Unauthorized");
            }
        } catch (IOException | InterruptedException e) {
            return new PurchaseResult(false, "Request failed: " + e.getMessage());
        }
        return new PurchaseResult(false, "Unknown error");
    }
}
