package game.service;

import game.model.AuthResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class AuthServiceImpl implements AuthService {
    private static final String BASE_URL = "http://horseserver.com/api";
    private final HttpClient httpClient;

    public AuthServiceImpl() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public AuthResult register(String username, String password) {
        try {
            String json = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()) {
                case 200, 201 -> AuthResult.SUCCESS;
                case 409 -> AuthResult.USERNAME_TAKEN;
                case 400 -> AuthResult.INVALID_PASSWORD;
                default -> AuthResult.SERVER_ERROR;
            };

        } catch (Exception e) {
            return AuthResult.SERVER_ERROR;
        }
    }

    @Override
    public AuthResult login(String username, String password) {
        try {
            String json = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()) {
                case 200 -> AuthResult.SUCCESS;
                case 401 -> AuthResult.WRONG_PASSWORD;
                case 404 -> AuthResult.USER_NOT_FOUND;
                default -> AuthResult.SERVER_ERROR;
            };
        } catch (Exception e) {
            return AuthResult.SERVER_ERROR;
        }
    }
}
