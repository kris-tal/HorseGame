package data;

public class ShopItem {
    private HorseType horseType;
    private int price;
    private boolean owned;
    private boolean upgrade;

    public ShopItem(HorseType horseType, int price, boolean owned, boolean upgrade) {
        this.horseType = horseType;
        this.price = price;
        this.owned = owned;
        this.upgrade = upgrade;
    }

    public HorseType getHorseType() { return horseType; }
    public int getPrice() { return price; }
    public boolean isOwned() { return owned; }
    public boolean isUpgrade() { return upgrade; }
}


