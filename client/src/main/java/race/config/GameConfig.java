package race.config;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GameConfig {
    
    @SerializedName("gameConfig")
    private GameConfigData gameConfigData;
    
    public static class GameConfigData {
        @SerializedName("defaultLanes")
        private int defaultLanes;
        
        @SerializedName("raceLength")
        private int raceLength;
        
        @SerializedName("trainingLength")
        private int trainingLength;
        
        @SerializedName("defaultSpeed")
        private float defaultSpeed;
        
        @SerializedName("defaultVisibleCols")
        private int defaultVisibleCols;
        
        @SerializedName("trainingPointsMultiplier")
        private float trainingPointsMultiplier;
        
        @SerializedName("sprintLevelMultiplier")
        private float sprintLevelMultiplier;
        
        @SerializedName("defaultSprint")
        private float defaultSprint;
        
        @SerializedName("defaultSprintCooldown")
        private float defaultSprintCooldown;
        
        // Getters
        public int getDefaultLanes() { return defaultLanes; }
        public int getRaceLength() { return raceLength; }
        public int getTrainingLength() { return trainingLength; }
        public float getDefaultSpeed() { return defaultSpeed; }
        public int getDefaultVisibleCols() { return defaultVisibleCols; }
        public float getTrainingPointsMultiplier() { return trainingPointsMultiplier; }
        public float getSprintLevelMultiplier() { return sprintLevelMultiplier; }
        public float getDefaultSprint() { return defaultSprint; }
        public float getDefaultSprintCooldown() { return defaultSprintCooldown; }
    }
    
    public GameConfigData getGameConfigData() {
        return gameConfigData;
    }

    public static GameConfig loadFromFile(String configPath) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = GameConfig.class.getClassLoader();
        }
        
        try (InputStream inputStream = classLoader.getResourceAsStream(configPath)) {
            if (inputStream == null) {
                throw new IOException("Plik konfiguracyjny nie istnieje w classpath: " + configPath);
            }
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line).append('\n');
                }
                
                Gson gson = new Gson();
                return gson.fromJson(jsonContent.toString(), GameConfig.class);
            }
        }
    }

    public static GameConfig loadDefault() throws IOException {
        return loadFromFile("config/gameConfig.json");
    }
}