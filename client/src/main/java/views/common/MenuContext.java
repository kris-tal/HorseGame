package views.common;

public class MenuContext {
    public enum Mode { SINGLEPLAYER, MULTIPLAYER }

    private Mode mode;
    private String chosenHorse;

    public void setMode(Mode mode) { this.mode = mode; }
    public Mode getMode() { return mode; }

    public void setChosenHorse(String horse) { this.chosenHorse = horse; }
    public String getChosenHorse() { return chosenHorse; }
}
