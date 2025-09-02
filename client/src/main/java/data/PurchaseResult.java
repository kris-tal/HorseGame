package data;

public class PurchaseResult {
    public boolean success;
    public String message;
    public int newBalance;

    public PurchaseResult(boolean success, String message) {
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
