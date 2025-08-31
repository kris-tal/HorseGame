package data;

public class PurchaseResult {
    public boolean success;
    public String message;

    public PurchaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    @Override
    public String toString() {
        return "PurchaseResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
