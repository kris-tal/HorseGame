package horsegame.screen;

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
import horsegame.HorseGame;
import horsegame.model.AuthResult;
import horsegame.service.AuthService;
import horsegame.service.AuthServiceImpl;
import horsegame.ui.UIItems;
import horsegame.ui.UIStyles;
import manager.ResourceManager;

public class LoginScreen implements Screen {
    private final HorseGame game;
    private final ResourceManager resourceManager;
    private final AuthService authService;
    private Stage stage;

    public LoginScreen(HorseGame game, ResourceManager resourceManager) {
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

        //login button
        TextButton loginButton = UIItems.getTextButton("log in");
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                Sound loginSuccessSound = ResourceManager.horseSound1;
                Sound loginFailSound = ResourceManager.snortSound;

                System.out.println("Login with: " + username + " / " + password);
                if (authService.login(username, password) != AuthResult.SUCCESS) {
                    System.out.println("Login failed");
                    loginFailSound.play();
                }
                else {
                    System.out.println("Login successful");
                    loginSuccessSound.play();
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
                loginButton.fire(new InputEvent() {{
                    setType(Type.touchDown);
                    setStage(stage);
                    setRelatedActor(loginButton);
                }});
                loginButton.fire(new InputEvent() {{
                    setType(Type.touchUp);
                    setStage(stage);
                    setRelatedActor(loginButton);
                }});
            }
        });

        float fieldWidth = Gdx.graphics.getWidth() / 3f;
        float fieldHeight = Gdx.graphics.getHeight() / 20f;

        //go to register screen
        Label registerLabel = new Label("I don't have an account..", UIStyles.getDefaultLabelStyle());
        registerLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game, resourceManager));
            }
        });

        //layout
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
