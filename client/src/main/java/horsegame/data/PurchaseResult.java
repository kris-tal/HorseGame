package horsegame.data;

import com.google.gson.Gson;

public class PurchaseResult {
    public boolean success;
    public String message;
    public Integer horseId;

    public PurchaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public PurchaseResult(boolean success, String message, Integer horseId) {
        this.success = success;
        this.message = message;
        this.horseId = horseId;
    }

    @Override
    public String toString() {
        return "PurchaseResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", horseId=" + horseId +
                '}';
    }

    public static PurchaseResult fromJson(String json) {
        return new Gson().fromJson(json, PurchaseResult.class);
    }
}
