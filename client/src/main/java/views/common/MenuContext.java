package views.common;

public class MenuContext {
    public enum RaceMode {SINGLEPLAYER, MULTIPLAYER}

    public enum LobbyMode {CREATE, JOIN}

    private RaceMode mode;
    private String chosenHorse;

    public void setMode(RaceMode raceMode) {
        this.mode = raceMode;
    }

    public RaceMode getMode() {
        return mode;
    }

    public void setChosenHorse(String horse) {
        this.chosenHorse = horse;
    }

    public String getChosenHorse() {
        return chosenHorse;
    }
}
