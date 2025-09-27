package services.listeners;

import data.lobby.PlayerInfoClient;
import java.util.HashMap;

public interface GameListener {
    void onProgressUpdate(HashMap<String, Integer> newProgress);
    void onGameWon(String winnerUsername); //Will be changed to contain some more information maybe
    void onGameClosed();//? , lobby close might happen during the game
}