package ui;

/**
 * Indicates an invalid move was made in a game
 */
public class ResponseException extends Exception {

    public ResponseException(String message) {
        super(message);
    }
}
