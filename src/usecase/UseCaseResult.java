package usecase;

public class UseCaseResult {
    private final boolean success;
    private final String message;

    public UseCaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static UseCaseResult ok(String message) {
        return new UseCaseResult(true, message);
    }

    public static UseCaseResult fail(String message) {
        return new UseCaseResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
