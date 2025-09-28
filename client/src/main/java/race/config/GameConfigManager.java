package race.config;

import java.io.IOException;

public class GameConfigManager {
    
    private static GameConfigManager instance;
    private GameConfig gameConfig;
    
    private GameConfigManager() {
    }

    public static synchronized GameConfigManager getInstance() {
        if (instance == null) {
            instance = new GameConfigManager();
        }
        return instance;
    }

    public void loadDefaultConfig() throws IOException {
        this.gameConfig = GameConfig.loadDefault();
    }


    public void setConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public GameConfig getConfig() {
        if (gameConfig == null) {
            try {
                loadDefaultConfig();
            } catch (IOException e) {
                throw new RuntimeException("Nie można załadować konfiguracji gry: " + e.getMessage(), e);
            }
        }
        return gameConfig;
    }

    public boolean isConfigLoaded() {
        return gameConfig != null;
    }
}
