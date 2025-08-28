package data;

public class ShopItem {
    private int id;
    private String name;
    private int price;
    private boolean owned;
    private boolean upgrade;

    public ShopItem(int id, String name, int price, boolean owned, boolean upgrade) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.owned = owned;
        this.upgrade = upgrade;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public boolean isOwned() { return owned; }
    public boolean isUpgrade() { return upgrade; }
}


