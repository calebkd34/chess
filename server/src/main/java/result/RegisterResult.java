package result;

public record RegisterResult(String message, String username, String authToken) {

    public RegisterResult(String message) {
        this(message, null, null);
    }

    public RegisterResult(String username, String authToken) {
        this(null, username, authToken);
    }
}
