package horsegame.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public interface ProtectedRequestsService {
    public HttpRequest.Builder ProtectedGet(String path);
    public HttpRequest.Builder ProtectedPost(String path, HttpRequest.BodyPublisher body);
    public HttpClient GetHttpClient();
}
