package game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.HorseGame;
import manager.ResourceManager;

public class LoginScreen implements Screen {
    private final HorseGame game;
    private final ResourceManager resourceManager;
    private Stage stage;

    public LoginScreen(HorseGame game, ResourceManager resourceManager) {
        this.game = game;
        this.resourceManager = resourceManager;
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
        Sound loginSound = ResourceManager.horseSound1;

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

        //login button
        TextButton loginButton = new TextButton("log in", skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loginSound.play();
                String username = usernameField.getText();
                String password = passwordField.getText();
                System.out.println("Login with: " + username + " / " + password);
                //add login logic !!!!
            }
        });

        float fieldWidth = Gdx.graphics.getWidth() / 3f;
        float fieldHeight = Gdx.graphics.getHeight() / 20f;

        //go to register screen
        Label registerLabel = new Label("I don't have an account..", skin);
        registerLabel.setColor(Color.PINK);
        registerLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen(game, resourceManager));
            }
        });

        //layout
        root.top();
        root.padTop(topPadding);
        root.add(logo).width(logoWidth).height(logoHeight).padBottom(mediumPadding).row();
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
        stage.dispose();
        //reszte disposuje w managerze
    }

}
