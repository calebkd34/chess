package result;

public record CreateGameResult(String message, Integer gameID) {

    public CreateGameResult(String message) {
        this(message, null);
    }

    public CreateGameResult(Integer gameID) {
        this(null, gameID);
    }
}
