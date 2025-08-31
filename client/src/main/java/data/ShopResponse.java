package data;

import java.util.List;

public class ShopResponse {
    public List<HorseToBuy> availableHorses;
    public List<Upgrade> upgrades;

    public static class HorseToBuy {
        public HorseType horseType;
        public int price;

        public HorseToBuy(HorseType horseType, int price) {
            this.horseType = horseType;
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
