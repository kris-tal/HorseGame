package views.login;

import services.auth.AuthService;
import data.auth.AuthCode;

public class LoginPresenter {
    private final LoginView view;
    private final AuthService authService;

    public LoginPresenter(LoginView view, AuthService authService) {
        this.view = view;
        this.authService = authService;
    }

    public void onLoginClicked(String username, String password) {
        var result = authService.login(username, password);
        if (result.returnedCode == AuthCode.SUCCESS) {
            view.showLoginSuccess();
            view.navigateToRanch();
        } else {
            view.showLoginFailure();
        }
    }

    public void onRegisterClicked() {
        view.navigateToRegister();
    }

    public void onExitPressed() {
        view.exitGame();
    }
}
