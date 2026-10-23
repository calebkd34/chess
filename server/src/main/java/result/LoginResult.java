package result;

public record LoginResult (String messge, String username, String authToken) {

    public LoginResult(String username, String authToken) {
        this(null, username, authToken);
    }

    public  LoginResult(String message) {
        this(message, null, null);
    }
}
