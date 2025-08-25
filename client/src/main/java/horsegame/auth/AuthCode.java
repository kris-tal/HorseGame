package horsegame.auth;

public enum AuthCode {
    SUCCESS,
    USERNAME_TAKEN,
    INVALID_PASSWORD,
    USER_NOT_FOUND,
    WRONG_PASSWORD,
    SERVER_ERROR,
    TIMEOUT,
    INTERRUPTED,
    INVALID_INPUT
}
