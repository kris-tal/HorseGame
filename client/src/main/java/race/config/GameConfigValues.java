package race.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GameConfigValues {
    
    private static JsonObject config = null;
    private static boolean loaded = false;
    
    private static void ensureLoaded() {
        if (!loaded) {
            try {
                loadConfig();
                loaded = true;
            } catch (IOException e) {
                throw new RuntimeException("Nie można załadować konfiguracji: " + e.getMessage(), e);
            }
        }
    }
    
    private static void loadConfig() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = GameConfigValues.class.getClassLoader();
        }

        try (InputStream inputStream = classLoader.getResourceAsStream("config/gameConfig.json")) {
            if (inputStream == null) {
                throw new IOException("Plik konfiguracyjny nie istnieje: config/gameConfig.json");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line).append('\n');
                }

                JsonObject root = JsonParser.parseString(jsonContent.toString()).getAsJsonObject();
                config = root.getAsJsonObject("gameConfig");
            }
        }
    }

    public static int getDefaultLanes() {
        ensureLoaded();
        return config.get("defaultLanes").getAsInt();
    }
    
    public static int getRaceLength() {
        ensureLoaded();
        return config.get("raceLength").getAsInt();
    }
    
    public static int getTrainingLength() {
        ensureLoaded();
        return config.get("trainingLength").getAsInt();
    }
    
    public static float getDefaultSpeed() {
        ensureLoaded();
        return config.get("defaultSpeed").getAsFloat();
    }
    
    public static int getDefaultVisibleCols() {
        ensureLoaded();
        return config.get("defaultVisibleCols").getAsInt();
    }
    
    public static float getTrainingPointsMultiplier() {
        ensureLoaded();
        return config.get("trainingPointsMultiplier").getAsFloat();
    }
    
    public static float getSprintLevelMultiplier() {
        ensureLoaded();
        return config.get("sprintLevelMultiplier").getAsFloat();
    }
    
    public static float getDefaultSprint() {
        ensureLoaded();
        return config.get("defaultSprint").getAsFloat();
    }
    
    public static float getDefaultSprintCooldown() {
        ensureLoaded();
        return config.get("defaultSprintCooldown").getAsFloat();
    }

    public static String getString(String key) {
        ensureLoaded();
        return config.get(key).getAsString();
    }
    
    public static int getInt(String key) {
        ensureLoaded();
        return config.get(key).getAsInt();
    }
    
    public static float getFloat(String key) {
        ensureLoaded();
        return config.get(key).getAsFloat();
    }
    
    public static boolean getBoolean(String key) {
        ensureLoaded();
        return config.get(key).getAsBoolean();
    }
}
