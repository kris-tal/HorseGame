package game.screen;

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
import game.HorseGame;
import game.model.AuthResult;
import game.service.AuthService;
import game.service.AuthServiceImpl;
import manager.ResourceManager;

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

        float topPadding = screenHeight / 10f;
        float bigPadding = screenHeight / 10f;
        float mediumPadding = screenHeight / 20f;
        float smallPadding = screenHeight / 40f;

        //loading assets
        Texture bgTexture = ResourceManager.authScreenBgTexture;
        Texture logoTexture = ResourceManager.logoTexture;
        Skin skin = ResourceManager.uiSkin;

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
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("username");

        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        TextField confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setMessageText("repeat password");
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');

        //register button
        TextButton registerButton = new TextButton("register!", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                Sound registerSuccessSound = ResourceManager.horseSound1;
                Sound registerFailSound = ResourceManager.snortSound;

                String confirmPassword = confirmPasswordField.getText();
                if (!password.equals(confirmPassword)) {
                    System.out.println("Passwords do not match.");
                    registerFailSound.play();
                    return;
                }

                System.out.println("Register with: " + username + " / " + password);
                if (authService.register(username, password) != AuthResult.SUCCESS) {
                    System.out.println("Registration failed.");
                    registerFailSound.play();
                }
                else {
                    System.out.println("Registration successful");
                    registerSuccessSound.play();
                    game.setScreen(new CharacterSelectionScreen(game, resourceManager));

                }
            }
        });

        float fieldWidth = Gdx.graphics.getWidth() / 3f;
        float fieldHeight = Gdx.graphics.getHeight() / 20f;

        //layout
        root.top();
        root.padTop(topPadding);
        root.add(logo).width(logoWidth).height(logoHeight).padBottom(mediumPadding).row();
        root.add(usernameField).width(fieldWidth).height(fieldHeight).padBottom(smallPadding).row();
        root.add(passwordField).width(fieldWidth).height(fieldHeight).padBottom(smallPadding).row();
        root.add(confirmPasswordField).width(fieldWidth).height(fieldHeight).padBottom(smallPadding).row();
        root.add(registerButton).width(fieldWidth / 4).height(fieldHeight / 2).padBottom(smallPadding).row();
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
        stage.dispose();
        //reszte disposuje w managerze
    }

}
