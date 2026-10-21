package result;

import java.util.Collection;

public class ListGamesResult {

    public record Game(int gameID, String whiteUsername, String blackUsername, String gameName) {}

    public ListGamesResult(String message, Collection<Game> Games) {

    }
}
