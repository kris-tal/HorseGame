package horsegame.service;

import horsegame.model.AuthResult;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    private final HttpClient client;
    private String authToken;
    private static final String BASE_URL = "http://localhost:5000";// tutaj dajemy to na czym pracuje nasz flask
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);
    private final Gson gson;

    public AuthServiceImpl() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(REQUEST_TIMEOUT)
                .build();
        this.authToken = null;
        this.gson = new Gson();
    }

    @Override
    public AuthResult login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return AuthResult.INVALID_INPUT;
        }

        try {
            String requestBody = gson.toJson(new LoginRequest(username, password));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .timeout(REQUEST_TIMEOUT)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                var jsonObject = gson.fromJson(response.body(), Map.class);
                if (jsonObject.containsKey("token")) {
                    authToken = (String) jsonObject.get("token");
                    return AuthResult.SUCCESS;
                } else {
                    return AuthResult.SERVER_ERROR;
                }
            } else if (response.statusCode() == 401) {
                return AuthResult.WRONG_PASSWORD;
            } else if (response.statusCode() == 404) {
                return AuthResult.USER_NOT_FOUND;
            } else {
                return AuthResult.SERVER_ERROR;
            }

        } catch (HttpTimeoutException e) {
            return AuthResult.TIMEOUT;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return AuthResult.INTERRUPTED;
        } catch (Exception e) {
            return AuthResult.SERVER_ERROR;
        }
    }

    @Override
    public AuthResult register(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return AuthResult.INVALID_INPUT;
        }

        try {
            String requestBody = gson.toJson(new LoginRequest(username, password));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/signup"))
                    .header("Content-Type", "application/json")
                    .timeout(REQUEST_TIMEOUT)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            System.out.println("===[DEBUG] Sending request to /signup ===");
            System.out.println("URI: " + request.uri());
            System.out.println("Headers: " + request.headers().map());
            System.out.println("Body: " + requestBody);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("===[DEBUG] Received response ===");
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());

            return switch (response.statusCode()) {
                case 201 -> AuthResult.SUCCESS;
                case 409 -> AuthResult.USERNAME_TAKEN;
                case 400 -> AuthResult.INVALID_INPUT;
                default -> AuthResult.SERVER_ERROR;
            };

        } catch (HttpTimeoutException e) {
            return AuthResult.TIMEOUT;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return AuthResult.INTERRUPTED;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return AuthResult.SERVER_ERROR;
        }
    }

    public String getAuthToken() {
        return authToken;
    }

    public void logout() {
        this.authToken = null;
    }

    private static class LoginRequest {
        String username;
        String password;
        String email;

        LoginRequest(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }

        LoginRequest(String username, String password) {
            this(username, password, null);
        }
    }

}
