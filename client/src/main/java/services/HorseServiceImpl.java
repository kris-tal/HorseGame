package services;

import com.google.gson.Gson;
import data.HorseData;
import data.HorsesResponse;
import services.managers.SessionManager;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HorseServiceImpl implements HorseService {
    private final Gson gson;
    public HorseServiceImpl() {
        gson = new Gson();
    }
    @Override
    public List<HorseData> getUserHorses() {
        HttpRequest request = SessionManager.getInstance()
                .getProtectedRequestsService().ProtectedGet("/horses").build();
        try {
            HttpResponse<String> result =
                    SessionManager.getInstance().getProtectedRequestsService()
                            .GetHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if(result.statusCode() == 200) {
                HorsesResponse response = gson.fromJson(result.body(), HorsesResponse.class);
                return response.getHorses();
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }
}
