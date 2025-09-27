package services.profile;

import com.google.gson.Gson;
import data.ProfileData;
import services.managers.SessionManager;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProfileServiceImpl implements ProfileService {
    private String username;
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
                ProfileData data =  gson.fromJson(result.body(), ProfileData.class);
                username =  data.getUsername();
                return data;
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }
    @Override
    public String getUsername() {
        if(username == null) return getUserData().getUsername();
        return username;
    }
}
