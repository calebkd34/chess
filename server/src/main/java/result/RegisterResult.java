package result;

public class RegisterResult {

    private final String username;
    private final String authToken;

    public RegisterResult(String message, String username, String authToken) {
        super(message);
        this.username = username;
        this.authToken = authToken;
    }
}
