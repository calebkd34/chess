package client;

/**
 * Indicates an invalid move was made in a game
 */
public class ServerException extends Exception {

    public ServerException(String message) {
        super(message);
    }
}
