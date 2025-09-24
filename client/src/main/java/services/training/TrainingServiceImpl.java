package services.training;

import com.google.gson.Gson;
import data.auth.AuthCode;
import data.auth.AuthResult;
import data.training.TrainingData;
import data.training.TrainingResponse;
import services.auth.AuthServiceImpl;
import services.managers.SessionManager;
import services.validation.ResponseValidator;
import services.validation.JsonResponseValidator;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static services.auth.AuthServiceImpl.BASE_URL;
import static services.auth.AuthServiceImpl.REQUEST_TIMEOUT;

public class TrainingServiceImpl implements TrainingService {
    private final Gson gson;
    private final ResponseValidator responseValidator;
    
    public TrainingServiceImpl() {
        this.gson = new Gson();
        this.responseValidator = new JsonResponseValidator();
    }
    
    public TrainingServiceImpl(ResponseValidator responseValidator) {
        this.gson = new Gson();
        this.responseValidator = responseValidator;
    }

    @Override
    public TrainingResponse performTraining(TrainingData data) {
        try {
            String requestBody = gson.toJson(data);

            HttpRequest request = SessionManager.getInstance()
                    .getProtectedRequestsService().ProtectedPost("/train",
                            HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = SessionManager.getInstance().getProtectedRequestsService().
                    GetHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), TrainingResponse.class);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TrainingResponse sendTrainingData(TrainingData data) {
        try {
            String requestBody = gson.toJson(data);

            HttpRequest request = SessionManager.getInstance()
                    .getProtectedRequestsService().ProtectedPost("/train",
                            HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8)).build();
            HttpResponse<String> response = SessionManager.getInstance().getProtectedRequestsService().
                    GetHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                return new TrainingResponse("{\"success\": false, \"message\": \"HTTP error: " + response.statusCode() + "\"}");
            }

            String responseBody = response.body();
            if (!responseValidator.isValidJson(responseBody)) {
                String errorMessage = responseValidator.extractErrorMessage(responseBody);
                return new TrainingResponse("{\"success\": false, \"message\": \"" + errorMessage + "\"}");
            }

            try {
                return gson.fromJson(responseBody, TrainingResponse.class);
            } catch (Exception e) {
                String safeResponse = "{\"success\": false, \"message\": \"Failed to parse server response\", \"raw_response\": \"" + 
                    responseBody.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r") + "\"}";
                return new TrainingResponse(safeResponse);
            }

        } catch (Exception e) {
            return new TrainingResponse("{\"success\": false, \"message\": \"Network error: " + e.getMessage() + "\"}");
        }
    }
}
