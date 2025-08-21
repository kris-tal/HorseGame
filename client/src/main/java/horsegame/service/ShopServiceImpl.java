package horsegame.service;

import com.google.gson.Gson;
import horsegame.data.PurchaseResult;
import horsegame.data.ShopResponse;

import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

public class ShopServiceImpl implements ShopService {

    private final HttpClient client;
    private final Gson gson;
    private String authToken;

    public static final String BASE_URL = "http://localhost:4000/api/shop";
    public static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    public ShopServiceImpl(String authToken) {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(REQUEST_TIMEOUT)
                .build();
        this.gson = new Gson();
        this.authToken = authToken;
    }

    @Override
    public ShopResponse getShopData() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .timeout(REQUEST_TIMEOUT)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), ShopResponse.class);
            } else if (response.statusCode() == 401) {
                throw new RuntimeException("Unauthorized: Invalid token");
            } else {
                throw new RuntimeException("Failed to fetch shop data: " + response.body());
            }

        } catch (HttpTimeoutException e) {
            throw new RuntimeException("Request timed out", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Request interrupted", e);
        } catch (Exception e) {
            throw new RuntimeException("Server error: " + e.getMessage(), e);
        }
    }

    @Override
    public PurchaseResult postHorsePurchase(int horseId) {
        return sendPurchaseRequest("/horse", horseId);
    }

    @Override
    public PurchaseResult postUpgradePurchase(int horseId) {
        return sendPurchaseRequest("/upgrade", horseId);
    }

    private PurchaseResult sendPurchaseRequest(String endpoint, int horseId) {
        try {
            String requestBody = gson.toJson(Map.of("horseId", horseId));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .timeout(REQUEST_TIMEOUT)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), PurchaseResult.class);
            } else if (response.statusCode() == 400) {
                return new PurchaseResult(false, "Invalid request data");
            } else if (response.statusCode() == 401) {
                return new PurchaseResult(false, "Unauthorized: Invalid token");
            } else {
                return new PurchaseResult(false, "Server error: " + response.body());
            }

        } catch (HttpTimeoutException e) {
            return new PurchaseResult(false, "Request timed out");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new PurchaseResult(false, "Request interrupted");
        } catch (Exception e) {
            return new PurchaseResult(false, "Server error: " + e.getMessage());
        }
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
