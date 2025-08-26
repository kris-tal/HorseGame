package data;

import java.util.List;

public class ShopResponse {
    public List<HorseToBuy> availableHorses;
    public List<Upgrade> upgrades;

    public static class HorseToBuy {
        public int id;
        public String race;
        public int price;

        public HorseToBuy(int id, String race, int price) {
            this.id = id;
            this.race = race;
            this.price = price;
        }
    }

    public static class Upgrade {
        public int horseId;
        public String race;
        public int price;

        public Upgrade(int horseId, String race, int price) {
            this.horseId = horseId;
            this.race = race;
            this.price = price;
        }
    }
}
