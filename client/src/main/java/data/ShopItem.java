package data;

public class ShopItem {
    private HorseType type;
    private int price;
    private boolean owned;
    private boolean upgrade;

    public ShopItem(HorseType type, int price, boolean owned, boolean upgrade) {
        this.type = type;
        this.price = price;
        this.owned = owned;
        this.upgrade = upgrade;
    }

    public int getId() { return type.getId(); }
    public String getName() { return type.getDisplayName(); }
    public String getAtlasKey() {return type.getAtlasKey();}
    public int getPrice() { return price; }
    public boolean isOwned() { return owned; }
    public boolean isUpgrade() { return upgrade; }

    @Override
    public String toString() {
        return String.format("<%d, %s, %d>", getId(), getName(), getPrice());
    }
}


