package horsegame.service;

import com.google.gson.Gson;
import horsegame.data.HorseData;
import horsegame.model.HorsesResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HorseServiceImpl implements HorseService {
    private final ProtectedRequestsService session;
    private final Gson gson;
    public HorseServiceImpl(ProtectedRequestsService session) {
        this.session = session;
        gson = new Gson();
    }
    @Override
    public List<HorseData> getUserHorses() {
        HttpRequest request = this.session.ProtectedGet("/horses").build();
        try {
            HttpResponse<String> result =
                    this.session.GetHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
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
