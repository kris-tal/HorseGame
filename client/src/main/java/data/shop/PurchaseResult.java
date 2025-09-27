package data.shop;

public class PurchaseResult {
    public int money;
    public boolean success;
    public String message;
    public boolean isUpgrade;

    public PurchaseResult(int money, boolean success, String message) {
        this.money = money;
        this.success = success;
        this.message = message;
        this.isUpgrade = false;
    }

    public PurchaseResult(int money, boolean success, String message, boolean isUpgrade) {
        this.money = money;
        this.success = success;
        this.message = message;
        this.isUpgrade = isUpgrade;
    }

    @Override
    public String toString() {
        return "PurchaseResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", newBalance=" + money +
                '}';
    }
}
