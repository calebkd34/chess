package result;

public class LoginResult {

    private final String authToken;
    private final String username;

    public LoginResult(String message, String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }
}
