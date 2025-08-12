package horsegame.service;

import horsegame.model.AuthResult;

public interface AuthService {
    AuthResult register(String username, String password);
    AuthResult login(String username, String password);
}
