package views.register;

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
import services.auth.AuthService;
import services.auth.AuthServiceImpl;
import views.common.UIStyles;
import services.ResourceManager;
import views.login.LoginScreen;
import views.ranch.RanchScreen;

public class RegisterScreen implements Screen {
    private final HorseGame game;
    private final ResourceManager resourceManager;
    private final AuthService authService;
    private Stage stage;

    public RegisterScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
        this.authService = new AuthServiceImpl();
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
        TextField usernameField = new TextField("", UIStyles.getTextFieldStyle());
        usernameField.setMessageText("username");

        TextField passwordField = new TextField("", UIStyles.getTextFieldStyle());
        passwordField.setMessageText("password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        TextField confirmPasswordField = new TextField("", UIStyles.getTextFieldStyle());
        confirmPasswordField.setMessageText("repeat password");
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');

        //register button
        TextButton registerButton = new TextButton("register!", UIStyles.getDefaultButtonStyle());
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                Sound registerSuccessSound = ResourceManager.horseSound1;
                Sound registerFailSound = ResourceManager.snortSound;

                String confirmPassword = confirmPasswordField.getText();
                if (!password.equals(confirmPassword)) {
                    Gdx.app.log("DEBUG", "Passwords do not match.");
                    registerFailSound.play();
                    return;
                }

                Gdx.app.log("DEBUG", "Register with: " + username + " / " + password);
                if (authService.register(username, password).returnedCode != AuthCode.SUCCESS) {
                    Gdx.app.log("DEBUG", "Registration failed.");
                    registerFailSound.play();
                }
                else {
                    Gdx.app.log("DEBUG", "Registration successful");
                    registerSuccessSound.play();
                    game.setScreen(new RanchScreen(game, resourceManager));
                }
            }
        });

        usernameField.setTextFieldListener((textField, c) -> {
            if (c == '\n' || c == '\r') {
                stage.setKeyboardFocus(passwordField);
            }
        });

        passwordField.setTextFieldListener((textField, c) -> {
            if (c == '\n' || c == '\r') {
                stage.setKeyboardFocus(confirmPasswordField);
            }
        });

        confirmPasswordField.setTextFieldListener((textField, c) -> {
            if (c == '\n' || c == '\r') {
                registerButton.fire(new InputEvent() {{
                    setType(Type.touchDown);
                    setStage(stage);
                    setRelatedActor(registerButton);
                }});
                registerButton.fire(new InputEvent() {{
                    setType(Type.touchUp);
                    setStage(stage);
                    setRelatedActor(registerButton);
                }});
            }
        });

        float fieldWidth = Gdx.graphics.getWidth() / 3f;
        float fieldHeight = Gdx.graphics.getHeight() / 20f;

        Label loginLabel = new Label("Actually I do have an account..", UIStyles.getDefaultLabelStyle());
        loginLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginScreen(game, resourceManager));
            }
        });

        //layout
        root.top();
        root.padTop(bigPadding);
        root.add(logo).width(logoWidth).height(logoHeight).padBottom(bigPadding).row();
        root.add(usernameField).width(fieldWidth).height(fieldHeight).padBottom(smallPadding).row();
        root.add(passwordField).width(fieldWidth).height(fieldHeight).padBottom(smallPadding).row();
        root.add(confirmPasswordField).width(fieldWidth).height(fieldHeight).padBottom(smallPadding).row();
        root.add(registerButton).width(fieldWidth / 4).height(fieldHeight / 2).padBottom(smallPadding).row();
        root.add(loginLabel).width(fieldWidth).padTop(smallPadding).row();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        stage.act(delta);
        stage.draw();
    }

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
        if (stage != null) {
            stage.dispose();
        }
    }

}
