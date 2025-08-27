package services;

import com.google.gson.Gson;
import data.HorseData;
import data.HorsesResponse;
import data.ProfileData;
import services.managers.SessionManager;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ProfileServiceImpl implements ProfileService {

    private final Gson gson;
    public ProfileServiceImpl() {
        gson = new Gson();
    }
    @Override
    public ProfileData getUserData() {
        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService().ProtectedGet("/profile").build();
        try {
            HttpResponse<String> result =
                    SessionManager.getInstance().getProtectedRequestsService()
                            .GetHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if(result.statusCode() == 200) {
                return gson.fromJson(result.body(), ProfileData.class);
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }
}
