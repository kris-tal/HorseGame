package data;

public class PurchaseResult {
    public int money;
    public boolean success;
    public String message;
    public int newBalance;

    public PurchaseResult(int money, boolean success, String message) {
        this.money = money;
        this.success = success;
        this.message = message;
    }

    public PurchaseResult(boolean success, String message, int newBalance) {
        this.success = success;
        this.message = message;
        this.newBalance = newBalance;
    }

    @Override
    public String toString() {
        return "PurchaseResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", newBalance=" + newBalance +
                '}';
    }
}
