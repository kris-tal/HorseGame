package manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {
    private static final AssetManager assetManager = new AssetManager();
    private static final InternalFileHandleResolver filePathResolver =  new InternalFileHandleResolver();

    //asset paths
    public static final String AUTH_SCREEN_BG_PATH = "images/background/auth_screen_background.png";
    public static final String LOGO_PATH = "images/other/logo.png";
    public static final String UI_TEXTURE_PATH_ATLAS = "skins/uiskin.atlas";
    public static final String UI_SKIN_PATH = "skins/uiskin.json";

    //preloaded assets
    public static Texture authScreenBgTexture;
    public static Texture logoTexture;
    public static TextureAtlas uiTextureAtlas = new TextureAtlas(UI_TEXTURE_PATH_ATLAS);
    public static Skin uiSkin = new Skin(Gdx.files.internal(UI_SKIN_PATH), uiTextureAtlas);


    public ResourceManager() {
        assetManager.load(AUTH_SCREEN_BG_PATH, Texture.class);
        assetManager.load(LOGO_PATH, Texture.class);
        assetManager.load(UI_SKIN_PATH, Skin.class);
        assetManager.load(UI_TEXTURE_PATH_ATLAS, TextureAtlas.class);

        assetManager.finishLoading();

        authScreenBgTexture = assetManager.get(AUTH_SCREEN_BG_PATH, Texture.class);
        logoTexture = assetManager.get(LOGO_PATH, Texture.class);
        uiSkin = assetManager.get(UI_SKIN_PATH, Skin.class);
        uiTextureAtlas = assetManager.get(UI_TEXTURE_PATH_ATLAS, TextureAtlas.class);
    }

    //i'll need this later (maybe)
    public static void loadTexture(String path) {
        if (!assetManager.isLoaded(path)) {
            assetManager.setLoader(Texture.class, new TextureLoader(filePathResolver));
            assetManager.load(path, Texture.class);
            assetManager.finishLoadingAsset(path);
        }
    }

    public static Texture getTexture(String path) {
        if (assetManager.isLoaded(path)) {
            return assetManager.get(path, Texture.class);
        }
        Gdx.app.log("ResourceManager", "Texture not loaded: " + path);
        return null;
    }

    public static void loadSkin(String path) {
        if (!assetManager.isLoaded(path)) {
            assetManager.load(path, Skin.class);
            assetManager.finishLoadingAsset(path);
        }
    }

    public static Skin getSkin(String path) {
        if (assetManager.isLoaded(path)) {
            return assetManager.get(path, Skin.class);
        }
        Gdx.app.log("ResourceManager", "Skin not loaded: " + path);
        return null;
    }

    public void dispose() {
        assetManager.dispose();
        if (authScreenBgTexture != null) authScreenBgTexture.dispose();
        if (logoTexture != null) logoTexture.dispose();
        if (uiSkin != null) uiSkin.dispose();
    }
}