package data;

import java.util.List;

public class ShopResponse {
    public List<HorseToBuy> horses;
    public List<Upgrade> upgrades;

    public static class HorseToBuy {
        public String name;
        public int price;

        public HorseToBuy(String race, int price) {
            this.name = race;
            this.price = price;
        }
    }

    public static class Upgrade {
        public String name;
        public int price;

        public Upgrade(String race, int price) {
            this.name = race;
            this.price = price;
        }
    }
}
