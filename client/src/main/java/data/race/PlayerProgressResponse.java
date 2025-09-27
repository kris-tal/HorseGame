package data.race;

import java.util.List;

public class PlayerProgressResponse {
    public static class PlayerProgress {
        public String getUsername() {
            return username;
        }

        String username;

        public int getProgress() {
            return progress;
        }

        int progress;
    }

    public List<PlayerProgress> getDistances() {
        return distances;
    }

    List<PlayerProgress> distances;
}
