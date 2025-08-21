package horsegame.data;

public class ShopItem {
    private int id;
    private String race;
    private int price;
    private boolean owned;
    private boolean upgrade;

    public ShopItem(int id, String race, int price, boolean owned, boolean upgrade) {
        this.id = id;
        this.race = race;
        this.price = price;
        this.owned = owned;
        this.upgrade = upgrade;
    }

    public int getId() { return id; }
    public String getRace() { return race; }
    public int getPrice() { return price; }
    public boolean isOwned() { return owned; }
    public boolean isUpgrade() { return upgrade; }
}


