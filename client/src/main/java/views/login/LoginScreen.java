package views.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.HorseGame;
import data.auth.AuthCode;
import views.ranch.RanchScreen;
import views.register.RegisterScreen;
import services.auth.AuthService;
import services.auth.AuthServiceImpl;
import views.common.UIFactory;
import views.common.UIStyles;
import services.ResourceManager;

public class LoginScreen implements Screen, LoginView {
    private final HorseGame game;
    private final ResourceManager resourceManager;
    private final LoginPresenter presenter;
    private Stage stage;

    public LoginScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
        this.presenter = new LoginPresenter(this, new AuthServiceImpl());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float bigPadding = screenHeight / 10f;
        float mediumPadding = screenHeight / 20f;
        float smallPadding = screenHeight / 40f;

        //loading assets
        Texture bgTexture = ResourceManager.authScreenBgTexture;
        Texture logoTexture = ResourceManager.logoTexture;

        //staging bg
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        //staging logo
        Image logo = new Image(logoTexture);
        logo.setScaling(Scaling.fit);
        float logoWidth = screenWidth / 2f;
        float logoHeight = logoTexture.getHeight() * (logoWidth / logoTexture.getWidth());

        //username / password field
        TextField usernameField = UIFactory.createTextField("username");

        TextField passwordField = UIFactory.createTextField("password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        //login button
        TextButton loginButton = UIFactory.createTextButton("log in");
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                Sound loginSuccessSound = ResourceManager.horseSound1;
                Sound loginFailSound = ResourceManager.snortSound;

                Gdx.app.log("DEBUG", "Login with: " + username + " / " + password);
                if (authService.login(username, password).returnedCode != AuthCode.SUCCESS) {
                    Gdx.app.log("DEBUG", "Login failed");
                    loginFailSound.play();
                }
                else {
                    Gdx.app.log("DEBUG", "Login successful");
                    loginSuccessSound.play();
                    game.setScreen(new RanchScreen(game, resourceManager));
                }
                presenter.onLoginClicked(usernameField.getText(), passwordField.getText());
            }
        });

        //register label
        Label registerLabel = new Label("I don't have an account..", UIStyles.getDefaultLabelStyle());
        registerLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                presenter.onRegisterClicked();
            }
        });

        //layout
        float fieldWidth = Gdx.graphics.getWidth() / 3f;
        float fieldHeight = Gdx.graphics.getHeight() / 20f;

        root.top();
        root.padTop(bigPadding);
        root.add(logo).width(logoWidth).height(logoHeight).padBottom(bigPadding).row();
        root.add(usernameField).width(fieldWidth).height(fieldHeight).padBottom(mediumPadding).row();
        root.add(passwordField).width(fieldWidth).height(fieldHeight).padBottom(mediumPadding).row();
        root.add(loginButton).width(fieldWidth / 4).height(fieldHeight / 2).padBottom(smallPadding).row();
        root.add(registerLabel).width(fieldWidth).padTop(smallPadding).row();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            presenter.onExitPressed();
        }
        stage.act(delta);
        stage.draw();
    }

    // LoginView methods
    @Override
    public void showLoginSuccess() {
        ResourceManager.horseSound1.play();
    }

    @Override
    public void showLoginFailure() {
        ResourceManager.snortSound.play();
    }

    @Override
    public void navigateToRegister() {
        game.setScreen(new RegisterScreen(game, resourceManager));
    }

    @Override
    public void navigateToRanch() {
        game.setScreen(new RanchScreen(game, resourceManager));
    }

    @Override
    public void exitGame() {
        Gdx.app.exit();
    }

    // LibGDX things
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if(stage != null) {
            stage.dispose();
        }
    }
}
